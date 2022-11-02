package ro.azs.kidsdevelopment.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java8.util.Optional
import ro.azs.kidsdevelopment.models.*


object FirestoreDataManager {

    private const val TAG = "FireStoreDataManager"

    private var _categoriesListenerRegistration: ListenerRegistration? = null

    private val userListenerRegistrations = ArrayList<ListenerRegistration>()

    private val _categoriesSubject: BehaviorSubject<List<CategoryGroup>> = BehaviorSubject.createDefault(emptyList())
    private val _userProfileSubject: BehaviorSubject<Optional<UserProfile>> = BehaviorSubject.createDefault(Optional.empty())
    private val _historySubject = BehaviorSubject.createDefault<List<HistoryItem>>(emptyList())
    private val _favouritesSubject: BehaviorSubject<List<FavoriteCategory>> = BehaviorSubject.createDefault(emptyList())

    private val dataProviders: Map<FirestoreCollection, FirestoreDataProvider<*>> =
        mapOf(CategorySectionType.bibleDiscoveries to BibleDiscoveriesProvider,
            CategorySectionType.bibleFavoriteTexts to BibleFavoriteTextsProvider,
            CategorySectionType.biblePrayerTexts to BiblePrayerTextsProvider,
            CategorySectionType.prayerSubjects to PrayerSubjectsProvider,
            CategorySectionType.prayerPeople to PrayerPeopleProvider
        )

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

                        _userProfileSubject.onNext(if (snapshot == null) Optional.empty() else Optional.of(snapshot.toObject(UserProfile::class.java)))
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

        FirebaseAuth.getInstance().addAuthStateListener(authStateListener)
    }

    fun stopDataSync() {
        _categoriesListenerRegistration?.remove()
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

    fun updateModel(collection: FirestoreCollection, model: FirestoreModel) {
        if (model.id != null) {
            getFirestoreUserDocument().collection(collection.name)
                .document(model.id!!)
                .set(model)
                .addOnSuccessListener { Logger.e(TAG, "added to  $collection with success!") }
                .addOnFailureListener { Logger.e(TAG, "added to  $collection with error") }
        } else {
            addModel(collection, model)
        }

    }

    fun addModel(collection: FirestoreCollection, model: FirestoreModel) {
        getFirestoreUserDocument().collection(collection.name)
            .add(model)
            .addOnSuccessListener { Logger.e(TAG, "added to  $collection with success!") }
            .addOnFailureListener { Logger.e(TAG, "added to  $collection with error") }
    }

    fun removeModel(collection: FirestoreCollection, model: FirestoreModel) {
        getFirestoreUserDocument().collection(collection.name)
            .document(model.id!!).delete()
            .addOnSuccessListener { Logger.e(TAG, "removed from $collection with success!") }
            .addOnFailureListener { Logger.e(TAG, "removed from $collection with error") }
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

    fun getSectionDataProvider(sectionType: CategorySectionType) : FirestoreDataProvider<*>? {
        return dataProviders[sectionType]
    }

    abstract class FirestoreDataProvider<T : FirestoreModel>(private val firestoreCollection: FirestoreCollection, private val modelClass: Class<T>) {
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

                    Logger.e(firestoreCollection.name, "received new! ")
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

    object BibleDiscoveriesProvider : FirestoreDataProvider<BibleDiscovery>(CategorySectionType.bibleDiscoveries, BibleDiscovery::class.java)

    object BibleFavoriteTextsProvider : FirestoreDataProvider<BibleFavoriteText>(CategorySectionType.bibleFavoriteTexts, BibleFavoriteText::class.java)

    object BiblePrayerTextsProvider : FirestoreDataProvider<BiblePrayerText>(CategorySectionType.biblePrayerTexts, BiblePrayerText::class.java)

    object PrayerSubjectsProvider : FirestoreDataProvider<PrayerSubject>(CategorySectionType.prayerSubjects, PrayerSubject::class.java)

    object PrayerPeopleProvider : FirestoreDataProvider<PrayerPeople>(CategorySectionType.prayerPeople, PrayerPeople::class.java)

}