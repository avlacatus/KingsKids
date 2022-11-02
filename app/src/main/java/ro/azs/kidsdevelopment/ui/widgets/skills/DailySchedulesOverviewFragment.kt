package ro.azs.kidsdevelopment.ui.widgets.skills

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class DailySchedulesOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = DailySchedulesOverviewPresenter::class.java.simpleName
    }
}

class DailySchedulesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { DailySchedulesOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.dailySchedule

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}