package ro.azs.kidsdevelopment.ui.categories

import ro.azs.kidsdevelopment.base.BaseContract
import ro.azs.kidsdevelopment.models.Category
import ro.azs.kidsdevelopment.models.CategoryGroup

class CategoriesContract {
    interface View : BaseContract.View {
        fun displaySections(groups: List<CategoryGroup>, favourites: List<Category>)
        fun openCategoryDetails(category: Category)
    }

    interface Presenter : BaseContract.Presenter {
        fun onCategorySelected(category: Category)
    }
}