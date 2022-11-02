package ro.azs.kidsdevelopment.ui.widgets.intellectual

import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter

class KnowledgeFindingsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = KnowledgeFindingsOverviewPresenter::class.java.simpleName
    }
}

class KnowledgeFindingsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { KnowledgeFindingsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.knowledgeFindings

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}