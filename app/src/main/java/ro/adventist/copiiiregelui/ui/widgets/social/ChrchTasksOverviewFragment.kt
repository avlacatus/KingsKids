package ro.adventist.copiiiregelui.ui.widgets.social

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class ChurchTasksOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = ChurchTasksOverviewPresenter::class.java.simpleName
    }
}

class ChurchTasksOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { ChurchTasksOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.churchTasks

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}