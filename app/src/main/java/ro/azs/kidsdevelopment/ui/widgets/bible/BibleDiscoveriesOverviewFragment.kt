package ro.azs.kidsdevelopment.ui.widgets.bible

import android.os.Bundle
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewContract
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter
import ro.azs.kidsdevelopment.utils.FirestoreDataManager

class BibleDiscoveriesOverviewPresenter(view: BaseWidgetOverviewContract.View) : BaseWidgetOverviewPresenter<BaseWidgetOverviewContract.View>(view),
    BaseWidgetOverviewContract.Presenter {

    companion object {
        private val TAG = BibleDiscoveriesOverviewPresenter::class.java.simpleName
    }
}

class BibleDiscoveriesOverviewFragment : BaseWidgetOverviewFragment<BaseWidgetOverviewContract.Presenter>(), BaseWidgetOverviewContract.View {

    private val presenter by lazy { BibleDiscoveriesOverviewPresenter(this) }

    override fun getPresenter(): BaseWidgetOverviewContract.Presenter {
        return presenter
    }

    override fun displayItems(items: List<Any>) {
        super.displayItems(items)
    }
    override fun getCategorySectionType(): CategorySectionType = CategorySectionType.bibleDiscoveries
}