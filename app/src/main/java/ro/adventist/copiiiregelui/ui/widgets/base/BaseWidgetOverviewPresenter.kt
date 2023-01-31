package ro.adventist.copiiiregelui.ui.widgets.base

import ro.adventist.copiiiregelui.base.BasePresenter
import ro.adventist.copiiiregelui.models.FirestoreModel
import ro.adventist.copiiiregelui.utils.FirestoreDataManager

open class BaseWidgetOverviewPresenter<T : BaseWidgetOverviewContract.View>(view: T) : BasePresenter<T>(view), BaseWidgetOverviewContract.Presenter {

    override fun onAttachView() {
        super.onAttachView()
        FirestoreDataManager.getSectionDataProvider(view.getCategorySectionType())?.let { dataManager ->
            dataManager.getDataSubject().distinctUntilChanged().subscribe({ items ->
                if (isViewAttached) {
                    view.displayItems(items.take(getMaxCountDisplayed()))
                }
            }, {})
        }
    }

    open fun getMaxCountDisplayed() = 3

    override fun onSeeAllItemsClicked() {
        view.openCategorySectionDetails()
    }

    override fun onAddNewItemClicked() {
        view.openNewEntry()
    }

    override fun onItemClicked(item: FirestoreModel) {
        view.openEditEntry(item)
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}