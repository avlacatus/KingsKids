package ro.adventist.copiiiregelui.ui.favorites

import ro.adventist.copiiiregelui.base.BaseContract
import ro.adventist.copiiiregelui.models.Category
import ro.adventist.copiiiregelui.models.CategoryGroup

class FavoritesContract {
    interface View : BaseContract.View {
        fun displaySections(categories: List<CategoryGroup>)
        fun openCategoryDetails(category: Category)
    }

    interface Presenter : BaseContract.Presenter {
        fun onCategorySelected(category: Category)
    }
}