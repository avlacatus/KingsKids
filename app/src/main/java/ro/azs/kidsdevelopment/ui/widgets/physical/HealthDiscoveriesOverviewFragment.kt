package ro.azs.kidsdevelopment.ui.widgets.physical

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class HealthDiscoveriesOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = HealthDiscoveriesOverviewPresenter::class.java.simpleName
    }
}

class HealthDiscoveriesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { HealthMetricsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.healthDiscoveries

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}