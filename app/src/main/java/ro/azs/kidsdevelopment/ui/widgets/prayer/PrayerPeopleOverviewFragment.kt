package ro.azs.kidsdevelopment.ui.widgets.prayer

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter
import ro.azs.kidsdevelopment.utils.FirestoreDataManager

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