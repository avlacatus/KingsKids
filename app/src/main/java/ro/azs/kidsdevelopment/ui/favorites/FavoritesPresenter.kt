package ro.azs.kidsdevelopment.ui.favorites

import io.reactivex.rxjava3.core.Observable
import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.Category
import ro.azs.kidsdevelopment.models.CategoryGroup
import ro.azs.kidsdevelopment.utils.FirestoreDataManager
import ro.azs.kidsdevelopment.utils.Logger

class FavoritesPresenter(view: FavoritesContract.View) : BasePresenter<FavoritesContract.View>(view), FavoritesContract.Presenter {

    override fun onAttachView() {
        super.onAttachView()

        addPresenterSubscription(Observable.combineLatest(FirestoreDataManager.categoryGroups, FirestoreDataManager.userData) { categoryGroups, userData ->
            Pair<List<CategoryGroup>, List<Category>>(
                categoryGroups,
                userData.map { it.favouriteCategories.map { favoriteCategory -> Category(favoriteCategory.type) } }.orElseGet { emptyList() })
        }.map { categoriesFavoritesPair ->
            categoriesFavoritesPair.first.mapNotNull { group ->
                group.categories.intersect(categoriesFavoritesPair.second.toSet()).let { intersection ->
                    if (intersection.isEmpty()) {
                        null
                    } else {
                        CategoryGroup(group.type, group.order, intersection.toList())
                    }
                }
            }
        }.subscribe({ categoryGroups ->
            if (isViewAttached) {
                view.displaySections(categoryGroups)
            }
        }, { e -> Logger.e(TAG, "error fetching categories: ${e.message}") })
        )
    }

    override fun onCategorySelected(category: Category) {
        view.openCategoryDetails(category)
    }

    companion object {
        private val TAG = FavoritesPresenter::class.java.simpleName
    }

}