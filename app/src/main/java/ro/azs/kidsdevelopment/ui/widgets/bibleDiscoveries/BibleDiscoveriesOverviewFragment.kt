package ro.azs.kidsdevelopment.ui.widgets.bibleDiscoveries

import android.os.Bundle
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment

class BibleDiscoveriesOverviewFragment : BaseWidgetOverviewFragment<BibleDiscoveriesOverviewContract.Presenter>(), BibleDiscoveriesOverviewContract.View {

    private lateinit var presenter: BibleDiscoveriesOverviewPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.titleRes.set(R.string.bibleDiscoveriesTitle)
        viewModel.hintRes.set(R.string.bibleDiscoveriesHint)
        viewModel.labelAddRes.set(R.string.addNewDiscovery)
    }

    override fun getPresenter(): BibleDiscoveriesOverviewContract.Presenter {
        if (!this::presenter.isInitialized) {
            presenter = BibleDiscoveriesOverviewPresenter(this)
        }
        return presenter
    }
}