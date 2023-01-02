package ro.azs.kidsdevelopment.ui.user

import ro.azs.kidsdevelopment.base.BaseContract
import ro.azs.kidsdevelopment.models.UserProfile

class UserDetailsContract {

    interface View : BaseContract.View {

        fun openLogIn()
        fun setupLoggedOutPage()
        fun setupUserProfile(user: UserProfile)
    }

    interface Presenter : BaseContract.Presenter {
        fun onLogInClicked()

        fun onUserLoggedIn()

        fun onUserProfileEdited()

        fun onLogInError()

        fun onLogOutClicked()

        fun onDeleteAccountClicked()
    }
}