package ro.adventist.copiiiregelui.ui.widgets.finance

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class LendsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = LendsOverviewPresenter::class.java.simpleName
    }
}

class LendsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { LendsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.lends

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}