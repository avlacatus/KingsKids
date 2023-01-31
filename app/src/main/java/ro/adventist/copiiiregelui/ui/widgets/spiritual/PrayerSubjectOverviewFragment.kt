package ro.adventist.copiiiregelui.ui.widgets.spiritual

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class PrayerSubjectsOverviewPresenter(view: BaseWidgetOverviewContract.View) : BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = PrayerSubjectsOverviewPresenter::class.java.simpleName
    }
}

class PrayerSubjectsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { PrayerSubjectsOverviewPresenter(this) }
    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.prayerSubjects

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}