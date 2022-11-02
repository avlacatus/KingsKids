package ro.azs.kidsdevelopment.ui.widgets.skills

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class UsefulActionsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = UsefulActionsOverviewPresenter::class.java.simpleName
    }
}

class UsefulActionsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { UsefulActionsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.usefulActions

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}