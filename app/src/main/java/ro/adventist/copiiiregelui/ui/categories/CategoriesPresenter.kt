package ro.adventist.copiiiregelui.ui.categories

import io.reactivex.rxjava3.core.Observable
import ro.adventist.copiiiregelui.base.BasePresenter
import ro.adventist.copiiiregelui.models.Category
import ro.adventist.copiiiregelui.models.CategoryGroup
import ro.adventist.copiiiregelui.utils.FirestoreDataManager
import ro.adventist.copiiiregelui.utils.Logger

class CategoriesPresenter(view: CategoriesContract.View) : BasePresenter<CategoriesContract.View>(view), CategoriesContract.Presenter {

    override fun onAttachView() {
        super.onAttachView()

        addPresenterSubscription(Observable.combineLatest(FirestoreDataManager.categoryGroups, FirestoreDataManager.userData) { categoryGroups, userData ->
            Pair<List<CategoryGroup>, List<Category>>(
                categoryGroups,
                userData.map { it.favouriteCategories.map { favoriteCategory -> Category(favoriteCategory.type) } }.orElseGet { emptyList() })
        }.retry(3).subscribe({ categoryGroups ->
            if (isViewAttached) {
                view.displaySections(categoryGroups.first, categoryGroups.second)
            }
        }, { e -> Logger.e(TAG, "error fetching categories: ${e.message}") }))
    }

    override fun onCategorySelected(category: Category) {
        view.openCategoryDetails(category)
    }

    companion object {
        private val TAG = CategoriesPresenter::class.java.simpleName
    }

}