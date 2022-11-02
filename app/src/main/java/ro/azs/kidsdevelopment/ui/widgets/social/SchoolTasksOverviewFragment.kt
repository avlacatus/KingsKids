package ro.azs.kidsdevelopment.ui.widgets.social

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

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