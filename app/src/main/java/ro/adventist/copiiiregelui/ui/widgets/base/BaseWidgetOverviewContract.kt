package ro.adventist.copiiiregelui.ui.widgets.base

import ro.adventist.copiiiregelui.base.BaseContract
import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.models.FirestoreModel

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