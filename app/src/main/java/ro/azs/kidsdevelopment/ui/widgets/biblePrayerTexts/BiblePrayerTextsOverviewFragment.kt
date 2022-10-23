package ro.azs.kidsdevelopment.ui.widgets.biblePrayerTexts

import android.os.Bundle
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment

class BiblePrayerTextsOverviewFragment : BaseWidgetOverviewFragment<BiblePrayerTextsOverviewContract.Presenter>(), BiblePrayerTextsOverviewContract.View {

    private lateinit var presenter: BiblePrayerTextsOverviewPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.titleRes.set(R.string.biblePrayerTextsTitle)
        viewModel.hintRes.set(R.string.biblePrayerTextsHint)
        viewModel.labelAddRes.set(R.string.addPrayerText)
    }

    override fun getPresenter(): BiblePrayerTextsOverviewContract.Presenter {
        if (!this::presenter.isInitialized) {
            presenter = BiblePrayerTextsOverviewPresenter(this)
        }
        return presenter
    }
}