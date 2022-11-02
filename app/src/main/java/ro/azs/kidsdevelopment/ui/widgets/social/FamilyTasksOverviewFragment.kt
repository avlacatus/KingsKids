package ro.azs.kidsdevelopment.ui.widgets.social

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class FamilyTasksOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = FamilyTasksOverviewPresenter::class.java.simpleName
    }
}

class FamilyTasksOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { FamilyTasksOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.familyTasks

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}