package ro.adventist.copiiiregelui.ui.history

import java8.util.Optional
import ro.adventist.copiiiregelui.base.BasePresenter
import ro.adventist.copiiiregelui.models.*
import ro.adventist.copiiiregelui.utils.FirestoreDataManager
import ro.adventist.copiiiregelui.utils.Logger

class HistoryPresenter(view: HistoryContract.View) : BasePresenter<HistoryContract.View>(view),
    HistoryContract.Presenter {

    private var historyItems: List<HistoryItem>? = null
    override fun onAttachView() {
        super.onAttachView()
        addPresenterSubscription(
            FirestoreDataManager.userData.map {
                if (it.isPresent) {
                    Optional.of(it.get().historyItems)
                } else Optional.empty()
            }.distinctUntilChanged()
                .subscribe({ optionalItems ->
                    historyItems = optionalItems.orElseGet { null }
                    if (isViewAttached) {
                        ensureViewData()
                    }
                }, { e -> Logger.e(TAG, "error fetching categories: ${e.message}") })
        )

        ensureViewData()
    }

    private fun ensureViewData() {
        historyItems?.let {
            view.displayHistoryItems(it)
        } ?: kotlin.run {
            view.setupLoggedOutView()
        }
    }


    companion object {
        private val TAG = HistoryPresenter::class.java.simpleName
    }
}