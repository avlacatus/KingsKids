package ro.azs.kidsdevelopment.ui.widgets.skills

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

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