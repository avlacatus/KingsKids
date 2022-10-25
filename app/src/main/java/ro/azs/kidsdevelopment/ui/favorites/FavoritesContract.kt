package ro.azs.kidsdevelopment.ui.favorites

import ro.azs.kidsdevelopment.base.BaseContract
import ro.azs.kidsdevelopment.models.Category
import ro.azs.kidsdevelopment.models.CategoryGroup

class FavoritesContract {
    interface View : BaseContract.View {
        fun displaySections(categories: List<CategoryGroup>)
        fun openCategoryDetails(category: Category)
    }

    interface Presenter : BaseContract.Presenter {
        fun onCategorySelected(category: Category)
    }
}