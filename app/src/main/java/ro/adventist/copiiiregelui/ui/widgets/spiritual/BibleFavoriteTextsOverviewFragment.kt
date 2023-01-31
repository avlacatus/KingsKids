package ro.adventist.copiiiregelui.ui.widgets.spiritual

import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewContract
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewPresenter

class BibleFavoriteTextsOverviewPresenter(view: BaseWidgetOverviewContract.View) :
    BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {


    companion object {
        private val TAG = BibleFavoriteTextsOverviewPresenter::class.java.simpleName
    }
}

class BibleFavoriteTextsOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { BibleFavoriteTextsOverviewPresenter(this) }

    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.bibleFavoriteTexts

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }
}