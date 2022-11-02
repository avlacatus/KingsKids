package ro.azs.kidsdevelopment.ui.widgets.spiritual

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

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