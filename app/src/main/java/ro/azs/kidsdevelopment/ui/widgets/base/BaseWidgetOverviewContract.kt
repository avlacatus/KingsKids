package ro.azs.kidsdevelopment.ui.widgets.base

import ro.azs.kidsdevelopment.base.BaseContract
import ro.azs.kidsdevelopment.models.CategorySectionType

class BaseWidgetOverviewContract {

    interface View : BaseContract.View {
        fun displayItems(items: List<Any>)
        fun getCategorySectionType(): CategorySectionType
    }

    interface Presenter : BaseContract.Presenter {
        fun onSeeAllItemsClicked()
        fun onAddNewItemClicked()
        fun onItemClicked(item: Any)
    }
}