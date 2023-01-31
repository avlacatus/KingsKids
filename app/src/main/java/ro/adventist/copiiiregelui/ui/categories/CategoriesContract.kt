package ro.adventist.copiiiregelui.ui.categories

import ro.adventist.copiiiregelui.base.BaseContract
import ro.adventist.copiiiregelui.models.Category
import ro.adventist.copiiiregelui.models.CategoryGroup

class CategoriesContract {
    interface View : BaseContract.View {
        fun displaySections(groups: List<CategoryGroup>, favourites: List<Category>)
        fun openCategoryDetails(category: Category)
    }

    interface Presenter : BaseContract.Presenter {
        fun onCategorySelected(category: Category)
    }
}