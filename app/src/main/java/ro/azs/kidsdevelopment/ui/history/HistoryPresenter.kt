package ro.azs.kidsdevelopment.ui.history

import java8.util.Optional
import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.*
import ro.azs.kidsdevelopment.utils.FirestoreDataManager
import ro.azs.kidsdevelopment.utils.Logger

class HistoryPresenter(view: HistoryContract.View) : BasePresenter<HistoryContract.View>(view),
    HistoryContract.Presenter {

    private lateinit var historyItems: List<HistoryItem>
    override fun onAttachView() {
        super.onAttachView()
        addPresenterSubscription(
            FirestoreDataManager.userData.map {
                if (it.isPresent) {
                    Optional.of(it.get().historyItems)
                } else Optional.empty()
            }.distinctUntilChanged()
                .subscribe({ optionalItems ->
                    Logger.e("HistoryPresenter", "subscribe received $optionalItems")
                    optionalItems.orElseGet { null }?.let {
                        historyItems = it
                    }
                    if (isViewAttached) {
                        ensureViewData()
                    }
                }, { e -> Logger.e(TAG, "error fetching categories: ${e.message}") })
        )

        ensureViewData()
    }

    private fun ensureViewData() {
        if (this::historyItems.isInitialized) {
            view.displayHistoryItems(historyItems)
        } else {
            view.setupLoggedOutView()
        }
    }


    companion object {
        private val TAG = HistoryPresenter::class.java.simpleName
    }
}