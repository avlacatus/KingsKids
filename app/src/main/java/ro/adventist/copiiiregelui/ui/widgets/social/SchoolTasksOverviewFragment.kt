package ro.adventist.copiiiregelui.ui.widgets.social

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class SchoolTasksOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = SchoolTasksOverviewPresenter::class.java.simpleName
    }
}

class SchoolTasksOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { SchoolTasksOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.schoolTasks

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}