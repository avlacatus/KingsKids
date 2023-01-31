package ro.adventist.copiiiregelui.base

import android.content.Context
import android.os.IBinder
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ro.adventist.copiiiregelui.R
import ro.adventist.copiiiregelui.utils.LocaleUtils
import ro.adventist.copiiiregelui.utils.ViewUtils

open class BaseActivity<T : BaseContract.Presenter?> : AppCompatActivity(), BaseContract.View {
    protected var progressLayout: View? = null
    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        progressLayout = findViewById(R.id.progressLayout)
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        progressLayout = findViewById(R.id.progressLayout)
    }

    override fun onResume() {
        super.onResume()
        getPresenter()?.onAttachView()
    }

    override fun onPause() {
        super.onPause()
        getPresenter()?.onDetachView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        hideKeyboard(focusedViewForKeyboard.windowToken)
        super.finish()
    }

    override fun getPresenter(): T? {
        return null
    }

    override fun setProgressLayoutVisible(isVisible: Boolean) {
        if (progressLayout != null) {
            progressLayout!!.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    override fun showConfirmOperationDialog(title: Int,
        message: Int,
        positiveButtonMessage: Int,
        negativeButtonMessage: Int?,
        onConfirm: () -> Unit,
        onDismiss: (() -> Unit)?) {
        showConfirmOperationDialog(title, getString(message), positiveButtonMessage, negativeButtonMessage, onConfirm, onDismiss)
    }

    override fun showConfirmOperationDialog(title: Int,
        message: String,
        positiveButtonMessage: Int,
        negativeButtonMessage: Int?,
        onConfirm: () -> Unit,
        onDismiss: (() -> Unit)?) {
        val builder = AlertDialog.Builder(this).setTitle(title).setMessage(message).setPositiveButton(
            positiveButtonMessage
        ) { _, _ -> onConfirm.invoke() }

        if (negativeButtonMessage != null) {
            builder.setNegativeButton(negativeButtonMessage) { _, _ -> onDismiss?.invoke() }
        }
        builder.show()
    }

    override fun showToastMessage(messageId: Int) {
        showToastMessage(getString(messageId))
    }

    override fun showToastMessage(message: String) {
        showToast(message, Toast.LENGTH_SHORT)
    }

    override fun showLongToastMessage(messageId: Int) {
        showLongToastMessage(getString(messageId))
    }

    override fun showLongToastMessage(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    private fun showToast(message: String, length: Int) {
        Toast.makeText(this, message, length).show()
    }

    //If no view currently has focus, create a new one, just so we can grab a window token from it
    protected val focusedViewForKeyboard: View
        get() {
            var view = currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(this)
            }
            return view
        }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleUtils.getLocalizedContext(newBase))
    }

    companion object {
        protected fun hideKeyboard(iBinder: IBinder?) {
            ViewUtils.hideKeyboard(iBinder)
        }
    }
}