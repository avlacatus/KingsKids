package ro.adventist.copiiiregelui.ui.history

import ro.adventist.copiiiregelui.base.BaseContract
import ro.adventist.copiiiregelui.models.HistoryItem

class HistoryContract {

    interface View : BaseContract.View {
        fun displayHistoryItems(items: List<HistoryItem>)
        fun setupLoggedOutView()
    }

    interface Presenter : BaseContract.Presenter {
    }
}