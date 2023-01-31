package ro.adventist.copiiiregelui.ui.user.edit

import ro.adventist.copiiiregelui.base.BaseContract
import ro.adventist.copiiiregelui.models.UserProfile

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