package ro.azs.kidsdevelopment.ui.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import ro.azs.kidsdevelopment.models.CategoryType

@Deprecated("not implemented in v1")
class HomeViewModel : ViewModel() {

    val isLoggedIn = ObservableBoolean(false)
    val displayedDate = ObservableField("duminică, 12 decembrie 2021")
    val userGreeting = ObservableField("")
    val favoriteCategories: ArrayList<CategoryType> = ArrayList()
}