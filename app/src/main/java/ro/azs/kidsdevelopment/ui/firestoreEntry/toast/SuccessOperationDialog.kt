package ro.azs.kidsdevelopment.ui.firestoreEntry.toast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.DialogFragment
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.databinding.DialogSelectBibleReferenceBinding
import ro.azs.kidsdevelopment.databinding.LayoutToastOperationSuccessBinding
import ro.azs.kidsdevelopment.models.BibleBookType
import ro.azs.kidsdevelopment.models.BibleReference
import ro.azs.kidsdevelopment.models.Labeled
import ro.azs.kidsdevelopment.utils.AutoCompleteUtils
import ro.azs.kidsdevelopment.utils.Logger
import ro.azs.kidsdevelopment.utils.ViewUtils

class SuccessOperationDialog : DialogFragment() {

    private lateinit var binding: LayoutToastOperationSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
        setStyle(STYLE_NO_FRAME, R.style.Full_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_toast_operation_success, container, false)
        binding.message = arguments?.getString(ARG_MESSAGE) ?: "Succes!"
        return binding.root
    }



    companion object {
        private const val ARG_MESSAGE = "ARG_MESSAGE"
        fun getDialog(message: String, ): SuccessOperationDialog {
            return SuccessOperationDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_MESSAGE, message)
                }
            }
        }
    }
}