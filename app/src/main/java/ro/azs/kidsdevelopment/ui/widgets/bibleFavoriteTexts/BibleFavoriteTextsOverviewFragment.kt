package ro.azs.kidsdevelopment.ui.widgets.bibleFavoriteTexts

import android.os.Bundle
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment

class BibleFavoriteTextsOverviewFragment : BaseWidgetOverviewFragment<BibleFavoriteTextsOverviewContract.Presenter>(), BibleFavoriteTextsOverviewContract.View {

    private lateinit var presenter: BibleFavoriteTextsOverviewPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.titleRes.set(R.string.bibleFavoriteTextsTitle)
        viewModel.hintRes.set(R.string.bibleFavoriteTextsHint)
        viewModel.labelAddRes.set(R.string.addFavoriteText)
    }

    override fun getPresenter(): BibleFavoriteTextsOverviewContract.Presenter {
        if (!this::presenter.isInitialized) {
            presenter = BibleFavoriteTextsOverviewPresenter(this)
        }
        return presenter
    }
}