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
    private val _bibleDiscoveriesSubject: BehaviorSubject<List<BibleDiscovery>> = BehaviorSubject.createDefault(emptyList())
    private val _bibleFavoriteTextsSubject: BehaviorSubject<List<BibleFavoriteText>> = BehaviorSubject.createDefault(emptyList())
    private val _biblePrayerTextsSubject: BehaviorSubject<List<BiblePrayerText>> = BehaviorSubject.createDefault(emptyList())

    private val _userDataSubject: Observable<Optional<UserData>> =
        Observable.combineLatest(_userProfileSubject,
            _favouritesSubject,
            _historySubject,
            _bibleDiscoveriesSubject,
            _bibleFavoriteTextsSubject,
            _biblePrayerTextsSubject
        ) { userProfile, favourites, historyItems, bibleDiscoveries, bibleFavoriteTexts, biblePrayerTexts ->
            if (userProfile.isPresent) {
                Optional.of(UserData(userProfile.get(), favourites, historyItems, bibleDiscoveries, bibleFavoriteTexts, biblePrayerTexts))
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

                addUserListenerRegistration(getFirestoreUserDocument()
                    .collection(CategorySectionType.bibleDiscoveries.name)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Logger.e(TAG, "error adding  ${CategorySectionType.bibleDiscoveries} snapshotListener: ${e.message}")
                            return@addSnapshotListener
                        }

                        _bibleDiscoveriesSubject.onNext(
                            snapshot?.documents?.map { doc ->
                                doc.toObject(BibleDiscovery::class.java)?.withId(doc.id) as BibleDiscovery
                            } ?: emptyList())
                    })

                addUserListenerRegistration(getFirestoreUserDocument()
                    .collection(CategorySectionType.bibleFavoriteTexts.name)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Logger.e(TAG, "error adding ${CategorySectionType.bibleFavoriteTexts} snapshotListener: ${e.message}")
                            return@addSnapshotListener
                        }

                        _bibleFavoriteTextsSubject.onNext(
                            snapshot?.documents?.map { doc ->
                                doc.toObject(BibleFavoriteText::class.java)?.withId(doc.id) as BibleFavoriteText
                            } ?: emptyList())
                    })

                addUserListenerRegistration(getFirestoreUserDocument()
                    .collection(CategorySectionType.biblePrayerTexts.name)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Logger.e(TAG, "error adding ${CategorySectionType.biblePrayerTexts} snapshotListener: ${e.message}")
                            return@addSnapshotListener
                        }

                        _biblePrayerTextsSubject.onNext(
                            snapshot?.documents?.map { doc ->
                                doc.toObject(BiblePrayerText::class.java)?.withId(doc.id) as BiblePrayerText
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
        _categoriesListenerRegistration = FirebaseFirestore.getInstance().collection(FirestoreConstants.categoriesGroups.name).addSnapshotListener { snapshot, e ->
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
}