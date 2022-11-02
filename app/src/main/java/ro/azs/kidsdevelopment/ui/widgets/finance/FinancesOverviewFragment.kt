package ro.azs.kidsdevelopment.ui.widgets.finance

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class FinancesOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = FinancesOverviewPresenter::class.java.simpleName
    }
}

class FinancesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { FinancesOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.finances

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}