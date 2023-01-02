package ro.azs.kidsdevelopment.ui.widgets.physical

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class BodyRoutinesOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    override fun getMaxCountDisplayed() = 1

    companion object {
        private val TAG = BodyRoutinesOverviewPresenter::class.java.simpleName
    }
}

class BodyRoutinesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { BodyRoutinesOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.bodyRoutines

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}