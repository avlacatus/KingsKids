package ro.azs.kidsdevelopment.ui.widgets.skills

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class GoodDeedsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = GoodDeedsOverviewPresenter::class.java.simpleName
    }
}

class GoodDeedsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { GoodDeedsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.goodDeeds

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}