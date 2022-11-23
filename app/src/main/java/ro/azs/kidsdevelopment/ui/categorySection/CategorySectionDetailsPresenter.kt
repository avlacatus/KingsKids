package ro.azs.kidsdevelopment.ui.categorySection

import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.utils.FirestoreDataManager

class CategorySectionDetailsPresenter(view: CategorySectionDetailsContract.View, private val categorySection: CategorySectionType?) :
    BasePresenter<CategorySectionDetailsContract.View>(view),
    CategorySectionDetailsContract.Presenter {

    override fun onAttachView() {
        super.onAttachView()

        categorySection?.let {
            view.setupTitle(it.titleRes)
            view.setEmptyMessage(it.emptyRes)
            FirestoreDataManager.getSectionDataProvider(it)?.let { dataManager ->
                dataManager.getDataSubject().distinctUntilChanged().subscribe({ items ->
                    if (isViewAttached) {
                        view.displayItems(items)
                    }
                }, {})
            }
        }
    }

    companion object {
        private val TAG = CategorySectionDetailsPresenter::class.java.simpleName
    }
}