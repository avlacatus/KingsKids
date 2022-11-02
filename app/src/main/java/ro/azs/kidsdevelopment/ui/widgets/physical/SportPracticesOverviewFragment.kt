package ro.azs.kidsdevelopment.ui.widgets.physical

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class SportPracticesOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = SportPracticesOverviewPresenter::class.java.simpleName
    }
}

class SportPracticesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { SportPracticesOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.sportPractice

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}