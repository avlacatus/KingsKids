package ro.azs.kidsdevelopment.ui.home

import ro.azs.kidsdevelopment.base.BaseContract
import ro.azs.kidsdevelopment.models.CategoryType

@Deprecated("not implemented in v1")
class HomeContract {
    interface View : BaseContract.View {
        fun openLogIn()
        fun displayUserData(name: String, categories: List<CategoryType>)
        fun setupLoggedOutView()
    }

    interface Presenter : BaseContract.Presenter {
        fun onLogInClicked()
    }
}