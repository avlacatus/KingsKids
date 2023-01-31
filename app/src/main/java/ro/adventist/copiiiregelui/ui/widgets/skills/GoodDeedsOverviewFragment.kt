package ro.adventist.copiiiregelui.ui.widgets.skills

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class GoodDeedsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = GoodDeedsOverviewPresenter::class.java.simpleName
    }
}

class GoodDeedsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { GoodDeedsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.goodDeeds

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}