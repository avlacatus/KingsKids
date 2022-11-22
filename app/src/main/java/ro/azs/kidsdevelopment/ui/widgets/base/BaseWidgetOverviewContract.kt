package ro.azs.kidsdevelopment.ui.widgets.base

import ro.azs.kidsdevelopment.base.BaseContract
import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.models.FirestoreModel

class BaseWidgetOverviewContract {

    interface View : BaseContract.View {
        fun displayItems(items: List<Any>)
        fun getCategorySectionType(): CategorySectionType
        fun openCategorySectionDetails()
        fun openNewEntry()
        fun openEditEntry(model: FirestoreModel)
    }

    interface Presenter : BaseContract.Presenter {
        fun onSeeAllItemsClicked()
        fun onAddNewItemClicked()
        fun onItemClicked(item: FirestoreModel)
    }
}