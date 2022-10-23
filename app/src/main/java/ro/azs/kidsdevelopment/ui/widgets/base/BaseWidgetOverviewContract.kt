package ro.azs.kidsdevelopment.ui.widgets.base

import ro.azs.kidsdevelopment.base.BaseContract

class BaseWidgetOverviewContract {

    interface View : BaseContract.View {
        fun displayItems(items: List<Any>)

    }

    interface Presenter : BaseContract.Presenter {
        fun onSeeAllItemsClicked()
        fun onAddNewItemClicked()
        fun onItemClicked(item: Any)
    }
}