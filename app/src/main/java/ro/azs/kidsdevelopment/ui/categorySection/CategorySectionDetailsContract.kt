package ro.azs.kidsdevelopment.ui.categorySection

import ro.azs.kidsdevelopment.base.BaseContract

class CategorySectionDetailsContract {

    interface View : BaseContract.View {
        fun setupTitle(title: Int)
        fun displayItems(items: List<Any>)
        fun setEmptyMessage(emptyMessageRes: Int)
    }

    interface Presenter : BaseContract.Presenter {
    }
}