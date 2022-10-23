package ro.azs.kidsdevelopment.ui.home

import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.UserData
import ro.azs.kidsdevelopment.utils.FirestoreDataManager
import ro.azs.kidsdevelopment.utils.Logger

@Deprecated("not implemented in v1")
class HomePresenter(view: HomeContract.View) : BasePresenter<HomeContract.View>(view), HomeContract.Presenter {

    private var userData: UserData? = null
//    private var userName: String? = null
//    private var userFavoriteCategories: List<CategoryType> = emptyList()


    override fun onAttachView() {
        super.onAttachView()

        addPresenterSubscription(
            FirestoreDataManager.userData.distinctUntilChanged().subscribe({ userData ->
                this.userData = userData.orElseGet { null }
//                this.userName = userData.map { userData.get().profile.name }.orElseGet { null }
//                this.userFavoriteCategories =
//                    userData.map { userData.get().favouriteCategories.map { favoriteCategory -> favoriteCategory.type } }.orElseGet { emptyList<>() }
                if (isViewAttached) {
                    ensureViewData()
                }
            }, { e -> Logger.e(TAG, "error fetching user data: ${e.message}") })
        )
    }

    private fun ensureViewData() {
        userData?.let {
            view.displayUserData(it.profile.name, it.favouriteCategories.map { it.type })
        } ?: run {
            view.setupLoggedOutView()
        }

    }

    override fun onLogInClicked() {
        view.openLogIn()
    }

    companion object {
        private val TAG = HomePresenter::class.java.simpleName
    }

}