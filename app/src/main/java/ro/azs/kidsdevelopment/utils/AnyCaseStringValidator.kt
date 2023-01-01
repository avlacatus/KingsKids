package ro.azs.kidsdevelopment.utils

import ro.azs.kidsdevelopment.models.Labeled
import android.widget.AutoCompleteTextView
import ro.azs.kidsdevelopment.utils.AnyCaseStringValidator
import java.util.function.Consumer

class AnyCaseStringValidator<T : Labeled>(private val items: List<T>, private val onItemTextFixed: (T) -> Unit) : AutoCompleteTextView.Validator {
    override fun isValid(text: CharSequence): Boolean {
        return items.any { item -> item.name.contentEquals(text) || AutoCompleteUtils.normalizeIgnoreDiacritics(item.name).contentEquals(text) }
    }

    override fun fixText(invalidText: CharSequence?): CharSequence {
        val output = items.firstOrNull { item ->
            item.name.equals(invalidText.toString().trim(), true)
                    || AutoCompleteUtils.normalizeIgnoreDiacritics(item.name).equals(invalidText.toString().trim(), true)
        }

        return output?.let {
            onItemTextFixed.invoke(it)
            output.name
        } ?: kotlin.run { "" }
    }

    companion object {
        private val TAG = AnyCaseStringValidator::class.java.simpleName
    }
}