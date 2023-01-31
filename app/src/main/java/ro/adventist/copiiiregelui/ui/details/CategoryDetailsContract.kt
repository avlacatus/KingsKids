package ro.adventist.copiiiregelui.ui.details

import ro.adventist.copiiiregelui.base.BaseContract

class CategoryDetailsContract {

    interface View : BaseContract.View {
        fun displayDetails(titleRes: Int, iconRes: Int, headerRes: Int, messageRes: Int)
        fun setIsFavorite(isFavourite: Boolean)
        fun setFavoriteVisible(isVisible: Boolean)
        fun setupLoggedOutView()
        fun setupWidgets()
    }

    interface Presenter : BaseContract.Presenter {
        fun onFavouriteClicked(isFavourite: Boolean)
    }
}