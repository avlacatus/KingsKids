package ro.adventist.copiiiregelui.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java8.util.Optional
import ro.adventist.copiiiregelui.models.*

object FirestoreDataManager {

    private const val TAG = "FireStoreDataManager"

    private var _categoriesListenerRegistration: ListenerRegistration? = null
    private var _bibleBooksListenerRegistration: ListenerRegistration? = null

    private val userListenerRegistrations = ArrayList<ListenerRegistration>()

    private val _categoriesSubject: BehaviorSubject<List<CategoryGroup>> = BehaviorSubject.createDefault(emptyList())
    private val _bibleBooksSubject: BehaviorSubject<List<BibleBook>> = BehaviorSubject.createDefault(emptyList())

    private val _userProfileSubject: BehaviorSubject<Optional<UserProfile>> = BehaviorSubject.createDefault(Optional.empty())
    private val _historySubject = BehaviorSubject.createDefault<List<HistoryItem>>(emptyList())
    private val _favouritesSubject: BehaviorSubject<List<FavoriteCategory>> = BehaviorSubject.createDefault(emptyList())

    private val dataProviders: Map<FirestoreCollection, FirestoreDataProvider<*>> =
        CategorySectionType.values().map { it to FirestoreDataProvider(it, it.modelClass) }.toMap()

    private val _userDataSubject: Observable<Optional<UserData>> =
        Observable.combineLatest(_userProfileSubject,
            _favouritesSubject,
            _historySubject
        ) { userProfile, favourites, historyItems ->
            if (userProfile.isPresent) {
                Optional.of(UserData(userProfile.get(), favourites, historyItems))
            } else {
                Optional.empty()
            }
        }

    private val authStateListener: FirebaseAuth.AuthStateListener = object : FirebaseAuth.AuthStateListener {

        override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
            if (FirebaseAuth.getInstance().currentUser != null) {
                addUserListenerRegistration(getFirestoreUserDocument()
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Logger.e(TAG, "error adding user data snapshotListener: ${e.message}")
                            return@addSnapshotListener
                        }
                        val userProfile = snapshot?.toObject(UserProfile::class.java) ?: UserProfile()
                        _userProfileSubject.onNext(Optional.of(userProfile))
                    }
                )

                addUserListenerRegistration(getFirestoreUserDocument()
                    .collection(FirestoreConstants.history.name)
                    .orderBy(FirestoreFields.date.name, Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Logger.e(TAG, "error adding ${FirestoreConstants.history} snapshotListener: ${e.message}")
                            return@addSnapshotListener
                        }

                        _historySubject.onNext(
                            snapshot?.documents?.map { doc ->
                                doc.toObject(HistoryItem::class.java)?.withId(doc.id) as HistoryItem
                            } ?: emptyList()
                        )
                    }
                )


                addUserListenerRegistration(getFirestoreUserDocument()
                    .collection(FirestoreConstants.favoriteCategories.name)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Logger.e(TAG, "error adding  ${FirestoreConstants.favoriteCategories} snapshotListener: ${e.message}")
                            return@addSnapshotListener
                        }

                        _favouritesSubject.onNext(
                            snapshot?.documents?.map { doc ->
                                doc.toObject(FavoriteCategory::class.java)?.withId(doc.id) as FavoriteCategory
                            } ?: emptyList())
                    })

            } else {
                _userProfileSubject.onNext(Optional.empty())
                clearUserListenerRegistrations()
            }
        }

    }
    val categoryGroups: Observable<List<CategoryGroup>>
        get() = _categoriesSubject.distinctUntilChanged()

    val bibleBooks: Observable<List<BibleBook>>
        get() = _bibleBooksSubject.distinctUntilChanged()

    val userData: Observable<Optional<UserData>>
        get() = _userDataSubject.distinctUntilChanged()

    fun startDataSync() {
        _categoriesListenerRegistration =
            FirebaseFirestore.getInstance().collection(FirestoreConstants.categoriesGroups.name).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Logger.e(TAG, "error adding categories snapshotListener: ${e.message}")
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    _categoriesSubject.onNext(snapshot.documents.map { documentSnapshot ->
                        return@map CategoryGroup(CategoryGroupType.getCategoryGroupById(documentSnapshot.getString("type") ?: ""),
                            documentSnapshot.getLong("order") ?: 0,
                            (documentSnapshot.get(FirestoreConstants.categories.name) as List<Map<String, String>>).map { map ->
                                val categoryType = CategoryType.getCategoryById(map["type"] ?: "")
                                Category(categoryType)
                            })
                    }.sortedBy { categoryGroup -> categoryGroup.order })

                }
            }

        _bibleBooksListenerRegistration =
            FirebaseFirestore.getInstance().collection(FirestoreConstants.bibleBooks.name).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Logger.e(TAG, "error adding bibleBooks snapshotListener: ${e.message}")
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    _bibleBooksSubject.onNext(snapshot.documents.map {  doc ->
                        (doc.toObject(BibleBook::class.java) as BibleBook).copy(type =  BibleBookType.getBibleBook(doc.getString("type") ?: ""))
                    }.sortedBy { book -> book.order })
                }
            }

        FirebaseAuth.getInstance().addAuthStateListener(authStateListener)
    }

    fun stopDataSync() {
        _categoriesListenerRegistration?.remove()
        _bibleBooksListenerRegistration?.remove()
        clearUserListenerRegistrations()
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener)
    }

    fun updateUserProfile(userProfile: UserProfile, onSuccess: () -> Unit, onFailure: () -> Unit) {
        getFirestoreUserDocument()
            .set(userProfile)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }
    }

    fun updateModel(collection: FirestoreCollection, model: FirestoreModel, onSuccess: (() -> Unit)? = null,
        onFailure: ((Exception) -> Unit)? = null) {
        if (model.id != null) {
            getFirestoreUserDocument().collection(collection.name)
                .document(model.id!!)
                .set(model)
                .addOnSuccessListener {
                    onSuccess?.invoke()
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
        } else {
            addModel(collection, model, onSuccess, onFailure)
        }

    }

    fun addModel(collection: FirestoreCollection, model: FirestoreModel, onSuccess: (() -> Unit)? = null,
        onFailure: ((Exception) -> Unit)? = null) {
        getFirestoreUserDocument().collection(collection.name)
            .add(model)
            .addOnSuccessListener {
                onSuccess?.invoke()
            }
            .addOnFailureListener {
                onFailure?.invoke(it)
            }
    }

    fun removeModel(collection: FirestoreCollection, model: FirestoreModel, onSuccess: (() -> Unit)? = null,
        onFailure: ((Exception) -> Unit)? = null) {
        model.id?.let {
            getFirestoreUserDocument().collection(collection.name)
                .document(model.id!!).delete()
                .addOnSuccessListener {
                    onSuccess?.invoke()
                }
                .addOnFailureListener {
                    onFailure?.invoke(it)
                }
        }
    }

    private fun getFirestoreUserDocument(): DocumentReference {
        return FirebaseFirestore.getInstance()
            .collection(FirestoreConstants.users.name)
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
    }

    private fun addUserListenerRegistration(registration: ListenerRegistration) {
        userListenerRegistrations.add(registration)
    }

    private fun clearUserListenerRegistrations() {
        userListenerRegistrations.forEach { it.remove() }
    }

    fun getSectionDataProvider(sectionType: CategorySectionType): FirestoreDataProvider<*>? {
        return dataProviders[sectionType]
    }

    open class FirestoreDataProvider<T : FirestoreModel>(private val firestoreCollection: FirestoreCollection, private val modelClass: Class<T>) {
        private val _dataSubject: BehaviorSubject<List<T>> by lazy {
            startSync()
            BehaviorSubject.createDefault(emptyList())

        }

        private fun startSync() {
            addUserListenerRegistration(getFirestoreUserDocument()
                .collection(firestoreCollection.name)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Logger.e(TAG, "error adding ${firestoreCollection.name} snapshotListener: ${e.message}")
                        return@addSnapshotListener
                    }
                    _dataSubject.onNext(
                        snapshot?.documents?.map { doc ->
                            doc.toObject(modelClass)?.withId(doc.id) as T
                        } ?: emptyList())
                })
        }

        fun getDataSubject(): Observable<List<T>> {
            return _dataSubject
        }
    }
}