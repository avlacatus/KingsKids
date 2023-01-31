package ro.adventist.copiiiregelui.ui.widgets.spiritual

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class BibleDiscoveriesOverviewPresenter(view: BaseWidgetOverviewContract.View) : BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = BibleDiscoveriesOverviewPresenter::class.java.simpleName
    }
}

class BibleDiscoveriesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { BibleDiscoveriesOverviewPresenter(this) }

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.bibleDiscoveries
}