package ro.adventist.copiiiregelui.ui.widgets.spiritual

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class BiblePrayerTextsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = BiblePrayerTextsOverviewPresenter::class.java.simpleName
    }
}

class BiblePrayerTextsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { BiblePrayerTextsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.biblePrayerTexts

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}