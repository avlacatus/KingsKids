package ro.adventist.copiiiregelui.ui.widgets.physical

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class SportPracticesOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = SportPracticesOverviewPresenter::class.java.simpleName
    }
}

class SportPracticesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { SportPracticesOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.sportPractice

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}