package ro.azs.kidsdevelopment.ui.widgets.bibleDiscoveries

import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter
import ro.azs.kidsdevelopment.utils.FirestoreDataManager

class BibleDiscoveriesOverviewPresenter(view: BibleDiscoveriesOverviewContract.View) : BaseWidgetOverviewPresenter<BibleDiscoveriesOverviewContract.View>(view),
    BibleDiscoveriesOverviewContract.Presenter {

    override fun onAttachView() {
        super.onAttachView()
        FirestoreDataManager.userData.filter { it.isPresent }.map { it.get().bibleDiscoveries }.distinctUntilChanged().subscribe({ bibleDiscoveries ->
            if (isViewAttached) {
                view.displayItems(bibleDiscoveries)
            }
        }, {})
    }

    companion object {
        private val TAG = BibleDiscoveriesOverviewPresenter::class.java.simpleName
    }
}
