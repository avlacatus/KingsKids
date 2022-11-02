package ro.azs.kidsdevelopment.ui.widgets.base

import ro.azs.kidsdevelopment.base.BasePresenter
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
        view.showToastMessage("Vezi Toate - neimplementat")
    }

    override fun onAddNewItemClicked() {
        view.showToastMessage("Adauga item nou - neimplementat")
    }

    override fun onItemClicked(item: Any) {
        view.showToastMessage("click item - neimplementat")
    }
}