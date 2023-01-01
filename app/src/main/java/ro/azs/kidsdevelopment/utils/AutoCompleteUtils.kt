package ro.azs.kidsdevelopment.utils

import android.content.Context
import ro.azs.kidsdevelopment.models.Labeled
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import android.text.Editable
import android.view.View.OnFocusChangeListener
import android.text.TextUtils
import android.view.View
import java.text.Normalizer

object AutoCompleteUtils {
    @JvmStatic
    fun normalizeIgnoreDiacritics(string: String): String {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }

    fun <T : Labeled> setupAutoCompleteFieldValues(context: Context,
        autoCompleteTextView: AutoCompleteTextView,
        items: List<T>,
        selectedPosition: Int,
        onItemSelected: (T?) -> Unit,
        transformFunction: ((T?, TextView?) -> Unit)?) {
        autoCompleteTextView.threshold = 1

        autoCompleteTextView.setAdapter(object : AutoCompleteAdapter<T>(context, items) {
            override fun transformView(position: Int, item: T?, textView: TextView?) {
                transformFunction?.invoke(item, textView)
            }
        })
        autoCompleteTextView.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, _: View?, position: Int, id: Long -> onItemSelected.invoke(parent.getItemAtPosition(position) as T) }
        autoCompleteTextView.validator = AnyCaseStringValidator(items, onItemSelected)
        autoCompleteTextView.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) {
                    onItemSelected.invoke(null)
                }
            }
        })
        autoCompleteTextView.onFocusChangeListener = OnFocusChangeListener { v: View?, hasFocus: Boolean ->
            if (hasFocus) {
                autoCompleteTextView.showDropDown()
            } else {
                val text = autoCompleteTextView.text
                if (!TextUtils.isEmpty(text)) {
                    autoCompleteTextView.setText(autoCompleteTextView.validator.fixText(text))
                }
            }
        }
        autoCompleteTextView.setOnClickListener { v: View? ->
            if (autoCompleteTextView.text.isEmpty()) {
                autoCompleteTextView.showDropDown()
            }
        }
        val defValue = if (selectedPosition >= 0 && selectedPosition < items.size) items[selectedPosition] else null
        autoCompleteTextView.setText(defValue?.name ?: "", false)
        onItemSelected.invoke(defValue)
    }
}