package ro.azs.kidsdevelopment.ui.widgets.biblePrayerTexts

import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter
import ro.azs.kidsdevelopment.utils.FirestoreDataManager

class BiblePrayerTextsOverviewPresenter(view: BiblePrayerTextsOverviewContract.View) :
    BaseWidgetOverviewPresenter<BiblePrayerTextsOverviewContract.View>(view),
    BiblePrayerTextsOverviewContract.Presenter {

    override fun onAttachView() {
        super.onAttachView()
        FirestoreDataManager.userData.filter { it.isPresent }.map { it.get().biblePrayerTexts }.distinctUntilChanged().subscribe({ biblePrayerTexts ->
            if (isViewAttached) {
                view.displayItems(biblePrayerTexts)
            }
        }, {})
    }

    companion object {
        private val TAG = BiblePrayerTextsOverviewPresenter::class.java.simpleName
    }
}
