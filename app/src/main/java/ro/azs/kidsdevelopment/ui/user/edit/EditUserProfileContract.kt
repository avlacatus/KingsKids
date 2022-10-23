package ro.azs.kidsdevelopment.ui.user.edit

import ro.azs.kidsdevelopment.base.BaseContract
import ro.azs.kidsdevelopment.models.UserProfile

class EditUserProfileContract {

    interface View : BaseContract.View {
        fun setupUserDetailsPage(user: UserProfile)
        fun getUserProfileInput(): UserProfile
        fun closeWithOk()
    }

    interface Presenter : BaseContract.Presenter {
        fun onSaveClicked()
    }
}