package ro.azs.kidsdevelopment.ui.history

import ro.azs.kidsdevelopment.base.BaseContract
import ro.azs.kidsdevelopment.models.HistoryItem

class HistoryContract {

    interface View : BaseContract.View {
        fun displayHistoryItems(items: List<HistoryItem>)
        fun setupLoggedOutView()
    }

    interface Presenter : BaseContract.Presenter {
    }
}