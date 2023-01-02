package ro.azs.kidsdevelopment.ui.user

import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import ro.azs.kidsdevelopment.KidsDevelopmentApplication
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.UserProfile
import ro.azs.kidsdevelopment.utils.FirestoreDataManager
import ro.azs.kidsdevelopment.utils.Logger

class UserDetailsPresenter(view: UserDetailsContract.View) : BasePresenter<UserDetailsContract.View>(view), UserDetailsContract.Presenter {

    private var userProfile: UserProfile? = null

    override fun onAttachView() {
        super.onAttachView()
        addPresenterSubscription(FirestoreDataManager.userData.subscribe({ userData ->
            userProfile = userData.map { it.profile }.orElseGet { null }
            if (isViewAttached) {
                ensureViewData()
            }
        }, { e -> Logger.e("UserDetailsPresenter", "error getting userData: ${e.message}") }))

        ensureViewData()
    }

    private fun ensureViewData() {
        userProfile?.let {
            view.setupUserProfile(it)
        } ?: kotlin.run { view.setupLoggedOutPage() }

    }

    override fun onUserProfileEdited() {
    }

    override fun onLogInClicked() {
        view.openLogIn()
    }

    override fun onUserLoggedIn() {
    }

    override fun onLogInError() {
        view.showToastMessage("Eroare la autentificare!")
    }

    override fun onLogOutClicked() {
        view.showConfirmOperationDialog(
            title = R.string.warning,
            message = R.string.logoutMessage,
            positiveButtonMessage = R.string.logoutYesAction,
            negativeButtonMessage = R.string.logoutNoAction,
            { AuthUI.getInstance().signOut(KidsDevelopmentApplication.appContext).addOnCompleteListener {} },
            onDismiss = null
        )
    }

    override fun onDeleteAccountClicked() {
        view.showConfirmOperationDialog(
            title = R.string.warning,
            message = R.string.deleteAccountMessage,
            positiveButtonMessage = R.string.yes,
            negativeButtonMessage = R.string.no,
            {
                FirebaseAuth.getInstance().currentUser?.delete()?.addOnCompleteListener {
                    Logger.e(TAG, "user deleted")
                }

                // AuthUI.getInstance().signOut(KidsDevelopmentApplication.appContext).addOnCompleteListener {}

            },
            onDismiss = null
        )
    }

    companion object {
        private const val TAG = "UserDetailsPresenter"
    }
}