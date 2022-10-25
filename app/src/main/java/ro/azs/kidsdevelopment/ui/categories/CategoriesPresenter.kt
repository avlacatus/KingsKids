package ro.azs.kidsdevelopment.ui.categories

import io.reactivex.rxjava3.core.Observable
import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.Category
import ro.azs.kidsdevelopment.models.CategoryGroup
import ro.azs.kidsdevelopment.utils.FirestoreDataManager
import ro.azs.kidsdevelopment.utils.Logger

class CategoriesPresenter(view: CategoriesContract.View) : BasePresenter<CategoriesContract.View>(view), CategoriesContract.Presenter {

    override fun onAttachView() {
        super.onAttachView()

        addPresenterSubscription(Observable.combineLatest(FirestoreDataManager.categoryGroups, FirestoreDataManager.userData) { categoryGroups, userData ->
            Logger.w("CategoriesPResenter", "received new userData: ${userData.get()?.historyItems}")

            Pair<List<CategoryGroup>, List<Category>>(
                categoryGroups,
                userData.map { it.favouriteCategories.map { favoriteCategory -> Category(favoriteCategory.type) } }.orElseGet { emptyList() })
        }.subscribe({ categoryGroups ->
            if (isViewAttached) {
                view.displaySections(categoryGroups.first, categoryGroups.second)
            }
        }, { e -> Logger.e(TAG, "error fetching categories: ${e.message}") })
        )
    }

    override fun onCategorySelected(category: Category) {
        view.openCategoryDetails(category)
    }

    companion object {
        private val TAG = CategoriesPresenter::class.java.simpleName
    }

}