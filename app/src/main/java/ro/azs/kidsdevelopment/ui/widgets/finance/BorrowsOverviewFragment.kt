package ro.azs.kidsdevelopment.ui.widgets.finance

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class BorrowsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = BorrowsOverviewPresenter::class.java.simpleName
    }
}

class BorrowsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { BorrowsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.borrows

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}