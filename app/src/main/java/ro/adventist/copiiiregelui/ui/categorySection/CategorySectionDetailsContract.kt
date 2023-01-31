package ro.adventist.copiiiregelui.ui.categorySection

import ro.adventist.copiiiregelui.base.BaseContract

class CategorySectionDetailsContract {

    interface View : BaseContract.View {
        fun setupTitle(title: Int)
        fun displayItems(items: List<Any>)
        fun setEmptyMessage(emptyMessageRes: Int)
    }

    interface Presenter : BaseContract.Presenter {
    }
}