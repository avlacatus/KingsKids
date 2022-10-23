package ro.azs.kidsdevelopment.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.utils.ViewUtils
import java.util.*

abstract class BaseFragment<T : BaseContract.Presenter?> : Fragment(), BaseContract.View {
    private var baseListener: BaseContract.View? = null
    protected var progressLayout: View? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseListener = ViewUtils.getListener(this, BaseContract.View::class.java)
    }

    override fun onResume() {
        super.onResume()
        if (getPresenter() != null) {
            getPresenter()!!.onAttachView()
        }
    }

    override fun onPause() {
        super.onPause()
        if (getPresenter() != null) {
            getPresenter()!!.onDetachView()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressLayout = view.findViewById(R.id.progressLayout)
    }

    override fun getPresenter(): T? {
        return null
    }

    override fun setProgressLayoutVisible(isVisible: Boolean) {
        if (progressLayout != null) {
            progressLayout!!.visibility = if (isVisible) View.VISIBLE else View.GONE
        } else if (baseListener != null) {
            baseListener!!.setProgressLayoutVisible(isVisible)
        }
    }

    override fun showToastMessage(messageId: Int) {
        Objects.requireNonNull(baseListener)
        baseListener!!.showToastMessage(messageId)
    }

    override fun showToastMessage(message: String) {
        Objects.requireNonNull(baseListener)
        baseListener!!.showToastMessage(message)
    }

    override fun showLongToastMessage(messageId: Int) {
        Objects.requireNonNull(baseListener)
        baseListener!!.showLongToastMessage(messageId)
    }

    override fun showLongToastMessage(message: String) {
        Objects.requireNonNull(baseListener)
        baseListener!!.showLongToastMessage(message)
    }

    override fun showConfirmOperationDialog(title: Int,
        message: Int,
        positiveButtonMessage: Int,
        negativeButtonMessage: Int?,
        onConfirm: () -> Unit,
        onDismiss: (() -> Unit)?) {
        Objects.requireNonNull(baseListener)
        baseListener!!.showConfirmOperationDialog(title, message, positiveButtonMessage, negativeButtonMessage, onConfirm, onDismiss)
    }

    override fun showConfirmOperationDialog(title: Int,
        message: String,
        positiveButtonMessage: Int,
        negativeButtonMessage: Int?,
        onConfirm: () -> Unit,
        onDismiss: (() -> Unit)?) {
        Objects.requireNonNull(baseListener)
        baseListener!!.showConfirmOperationDialog(title, message, positiveButtonMessage, negativeButtonMessage, onConfirm, onDismiss)
    }
}