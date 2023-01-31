package ro.adventist.copiiiregelui.ui.widgets.spiritual

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class PrayerPeopleOverviewPresenter(view: BaseWidgetOverviewContract.View) : BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = PrayerPeopleOverviewPresenter::class.java.simpleName
    }
}

class PrayerPeopleOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { PrayerPeopleOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.prayerPeople

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}