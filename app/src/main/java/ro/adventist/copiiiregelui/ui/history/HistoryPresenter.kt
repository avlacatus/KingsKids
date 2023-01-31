package ro.adventist.copiiiregelui.ui.history

import java8.util.Optional
import ro.adventist.copiiiregelui.base.BasePresenter
import ro.adventist.copiiiregelui.models.*
import ro.adventist.copiiiregelui.utils.FirestoreDataManager
import ro.adventist.copiiiregelui.utils.Logger

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