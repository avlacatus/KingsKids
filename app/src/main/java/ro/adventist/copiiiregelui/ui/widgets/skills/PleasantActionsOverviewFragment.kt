package ro.adventist.copiiiregelui.ui.widgets.skills

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class PleasantActionsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = PleasantActionsOverviewPresenter::class.java.simpleName
    }
}

class PleasantActionsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { PleasantActionsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.pleasantActions

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}