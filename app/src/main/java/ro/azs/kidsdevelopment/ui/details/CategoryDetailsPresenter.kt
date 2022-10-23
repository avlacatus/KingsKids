package ro.azs.kidsdevelopment.ui.details

import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.CategoryType
import ro.azs.kidsdevelopment.models.FavoriteCategory
import ro.azs.kidsdevelopment.models.FirestoreConstants
import ro.azs.kidsdevelopment.models.UserData
import ro.azs.kidsdevelopment.utils.FirestoreDataManager
import ro.azs.kidsdevelopment.utils.Logger

class CategoryDetailsPresenter(view: CategoryDetailsContract.View, private val categoryId: String) : BasePresenter<CategoryDetailsContract.View>(view),
    CategoryDetailsContract.Presenter {

    private val categoryType = CategoryType.getCategoryById(categoryId)
    private var userData: UserData? = null
    private var favoriteCategory: FavoriteCategory? = null

    init {
        view.displayDetails(categoryType.labelRes, categoryType.iconRes, categoryType.headerRes, categoryType.messageRes)
    }

    override fun onAttachView() {
        super.onAttachView()
        addPresenterSubscription(
            FirestoreDataManager.userData.subscribe({ userData ->
                this.userData = userData.orElseGet { null }
                this.favoriteCategory = this.userData!!.favouriteCategories.firstOrNull { it.type == categoryType }
                if (isViewAttached) {
                    ensureViewData()
                }
            }, { e -> Logger.e(TAG, "error fetching categories: ${e.message}") })
        )

        ensureViewData()
    }

    private fun ensureViewData() {
        if (userData != null) {
            view.setFavoriteVisible(true)
            view.setIsFavorite(favoriteCategory != null)
            view.setupWidgets()
        } else {
            view.setFavoriteVisible(false)
            view.setupLoggedOutView()
        }
    }

    override fun onFavouriteClicked(isFavourite: Boolean) {
        if (isFavourite) {
            FirestoreDataManager.addModel(FirestoreConstants.favoriteCategories, FavoriteCategory(categoryType))
        } else {
            favoriteCategory?.let { FirestoreDataManager.removeModel(FirestoreConstants.favoriteCategories, it) }
        }
    }

    companion object {
        private const val TAG = "CategoryDetailsPresenter"
    }
}