package ro.azs.kidsdevelopment.ui.widgets.bibleFavoriteTexts

import android.os.Bundle
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter
import ro.azs.kidsdevelopment.utils.FirestoreDataManager

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