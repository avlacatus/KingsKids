package ro.adventist.copiiiregelui.ui.favorites

import io.reactivex.rxjava3.core.Observable
import java8.util.Optional
import ro.adventist.copiiiregelui.base.BasePresenter
import ro.adventist.copiiiregelui.models.Category
import ro.adventist.copiiiregelui.models.CategoryGroup
import ro.adventist.copiiiregelui.utils.FirestoreDataManager
import ro.adventist.copiiiregelui.utils.Logger

class FavoritesPresenter(view: FavoritesContract.View) : BasePresenter<FavoritesContract.View>(view), FavoritesContract.Presenter {

    override fun onAttachView() {
        super.onAttachView()

        addPresenterSubscription(Observable.combineLatest(FirestoreDataManager.categoryGroups, FirestoreDataManager.userData) { categoryGroups, userData ->
            Pair<List<CategoryGroup>, Optional<List<Category>>>(
                categoryGroups,
                if (userData.isPresent) {
                    Optional.of( userData.map { it.favouriteCategories.map { favoriteCategory -> Category(favoriteCategory.type) } }.orElseGet { emptyList() })
                } else Optional.empty())
        }
            .subscribe({ categoriesFavoritesPair ->
                if (categoriesFavoritesPair.second.isPresent) {
                    val categoryGroups = categoriesFavoritesPair.first.mapNotNull { group ->
                        group.categories.intersect(categoriesFavoritesPair.second.get().toSet()).let { intersection ->
                            if (intersection.isEmpty()) {
                                null
                            } else {
                                CategoryGroup(group.type, group.order, intersection.toList())
                            }
                        }
                    }
                    if (isViewAttached) {
                        view.displaySections(categoryGroups)
                    }
                } else {
                    view.setupLoggedOutView()
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