package ro.adventist.copiiiregelui.ui.widgets.intellectual

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class HobbiesOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = HobbiesOverviewPresenter::class.java.simpleName
    }
}

class HobbiesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { HobbiesOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.hobbies

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}