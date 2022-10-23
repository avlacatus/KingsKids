package ro.azs.kidsdevelopment.ui.widgets.bibleFavoriteTexts

import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewPresenter
import ro.azs.kidsdevelopment.utils.FirestoreDataManager

class BibleFavoriteTextsOverviewPresenter(view: BibleFavoriteTextsOverviewContract.View) :
    BaseWidgetOverviewPresenter<BibleFavoriteTextsOverviewContract.View>(view),
    BibleFavoriteTextsOverviewContract.Presenter {

    override fun onAttachView() {
        super.onAttachView()
        FirestoreDataManager.userData.filter { it.isPresent }.map { it.get().bibleFavoriteTexts }.distinctUntilChanged().subscribe({ bibleFavoriteTexts ->
            if (isViewAttached) {
                view.displayItems(bibleFavoriteTexts)
            }
        }, {})
    }

    companion object {
        private val TAG = BibleFavoriteTextsOverviewPresenter::class.java.simpleName
    }
}
