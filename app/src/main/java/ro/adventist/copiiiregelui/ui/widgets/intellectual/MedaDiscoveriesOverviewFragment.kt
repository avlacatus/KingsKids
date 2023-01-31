package ro.adventist.copiiiregelui.ui.widgets.intellectual

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class MediaDiscoveriesOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = MediaDiscoveriesOverviewPresenter::class.java.simpleName
    }
}

class MediaDiscoveriesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { MediaDiscoveriesOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.mediaDiscoveries

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}