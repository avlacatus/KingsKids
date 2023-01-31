package ro.adventist.copiiiregelui.ui.user.edit

import ro.adventist.copiiiregelui.R
import ro.adventist.copiiiregelui.base.BasePresenter
import ro.adventist.copiiiregelui.models.UserProfile
import ro.adventist.copiiiregelui.utils.FirestoreDataManager
import ro.adventist.copiiiregelui.utils.Logger

class EditUserProfilePresenter(view: EditUserProfileContract.View) :
    BasePresenter<EditUserProfileContract.View>(view), EditUserProfileContract.Presenter {

    private var userProfile: UserProfile? = null


    override fun onAttachView() {
        super.onAttachView()
        addPresenterSubscription(FirestoreDataManager.userData.subscribe({ userData ->
            userProfile = userData.map { it.profile }.orElseGet { null }
            if (isViewAttached) {
                view.setupUserDetailsPage(userProfile!!)
            }
        }, { e -> Logger.e("UserDetailsPresenter", "error getting userData: ${e.message}") }))

        if (userProfile != null) {
            view.setupUserDetailsPage(userProfile!!)
        }
    }


    override fun onSaveClicked() {
        FirestoreDataManager.updateUserProfile(view.getUserProfileInput(),
            { view.closeWithOk() },
            { view.showToastMessage(R.string.fui_error_unknown) })
    }

    companion object {
        private val TAG = EditUserProfilePresenter::class.java.simpleName
    }
}