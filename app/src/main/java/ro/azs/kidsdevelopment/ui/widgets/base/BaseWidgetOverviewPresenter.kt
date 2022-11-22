package ro.azs.kidsdevelopment.ui.widgets.base

import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.FirestoreModel
import ro.azs.kidsdevelopment.utils.FirestoreDataManager

open class BaseWidgetOverviewPresenter<T : BaseWidgetOverviewContract.View>(view: T) : BasePresenter<T>(view), BaseWidgetOverviewContract.Presenter {
    private val TAG = this.javaClass.simpleName

    override fun onAttachView() {
        super.onAttachView()
        FirestoreDataManager.getSectionDataProvider(view.getCategorySectionType())?.let { dataManager ->
            dataManager.getDataSubject().distinctUntilChanged().subscribe({ items ->
                if (isViewAttached) {
                    view.displayItems(items)
                }
            }, {})
        }
    }
    override fun onSeeAllItemsClicked() {
        view.openCategorySectionDetails()
    }

    override fun onAddNewItemClicked() {
        view.openNewEntry()
    }

    override fun onItemClicked(item: FirestoreModel) {
        view.openEditEntry(item)
    }
}