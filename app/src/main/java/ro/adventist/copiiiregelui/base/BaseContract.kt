package ro.adventist.copiiiregelui.base

import androidx.annotation.StringRes
import ro.adventist.copiiiregelui.R

class BaseContract {
    interface View {
        fun getPresenter(): Presenter?
        fun showToastMessage(@StringRes messageId: Int)
        fun showToastMessage(message: String)
        fun showLongToastMessage(@StringRes messageId: Int)
        fun showLongToastMessage(message: String)
        fun setProgressLayoutVisible(isVisible: Boolean)
        fun showConfirmOperationDialog(@StringRes title: Int = R.string.warning,
            @StringRes message: Int,
            @StringRes positiveButtonMessage: Int = R.string.yes,
            @StringRes negativeButtonMessage: Int? = R.string.cancel,
            onConfirm: () -> Unit,
            onDismiss: (() -> Unit)? = null)

        fun showConfirmOperationDialog(@StringRes title: Int = R.string.warning,
            message: String,
            @StringRes positiveButtonMessage: Int = R.string.yes,
            @StringRes negativeButtonMessage: Int? = R.string.cancel,
            onConfirm: () -> Unit,
            onDismiss: (() -> Unit)? = null)
    }

    interface Presenter {
        /**
         * called when the view is available, should only send the view here, but this will be a later implementation
         */
        fun onAttachView()

        /**
         * when the view is not available any more
         */
        fun onDetachView()
        fun subscribe()
        fun unSubscribe()
        val isViewAttached: Boolean
    }
}