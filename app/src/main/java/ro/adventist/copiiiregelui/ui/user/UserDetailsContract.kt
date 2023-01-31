package ro.adventist.copiiiregelui.ui.user

import ro.adventist.copiiiregelui.base.BaseContract
import ro.adventist.copiiiregelui.models.UserProfile

class UserDetailsContract {

    interface View : BaseContract.View {

        fun openLogIn()
        fun setupLoggedOutPage()
        fun setupUserProfile(user: UserProfile)
        fun openContactForm()
    }

    interface Presenter : BaseContract.Presenter {
        fun onLogInClicked()

        fun onUserLoggedIn()

        fun onUserProfileEdited()

        fun onLogInError()

        fun onLogOutClicked()

        fun onContactUsClicked()

        fun onDeleteAccountClicked()
    }
}