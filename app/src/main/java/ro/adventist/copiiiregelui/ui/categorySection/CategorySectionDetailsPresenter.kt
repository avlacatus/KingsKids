package ro.adventist.copiiiregelui.ui.categorySection

import ro.adventist.copiiiregelui.base.BasePresenter
import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.utils.FirestoreDataManager

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