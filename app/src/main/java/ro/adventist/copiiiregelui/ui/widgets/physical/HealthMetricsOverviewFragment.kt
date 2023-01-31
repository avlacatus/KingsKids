package ro.adventist.copiiiregelui.ui.widgets.physical

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class HealthMetricsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = HealthMetricsOverviewPresenter::class.java.simpleName
    }
}

class HealthMetricsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { HealthMetricsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.healthMetrics

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}