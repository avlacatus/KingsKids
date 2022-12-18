package ro.azs.kidsdevelopment.ui.firestoreEntry.control

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.ObservableField
import com.google.firebase.Timestamp
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.BibleFavoriteText
import ro.azs.kidsdevelopment.models.BibleReference
import ro.azs.kidsdevelopment.utils.ConversionUtils
import java.util.Date

open class EntryItemViewModel(@StringRes val label: Int, @LayoutRes val resId: Int) {
}

class LongTimestampItemViewModel(initialTimestamp: Timestamp? = null) : EntryItemViewModel(R.string.dateLabel, R.layout.entry_control_item_date) {
    val selectedDate = ObservableField(initialTimestamp ?: Timestamp(Date()))
    val dateLabel = ObservableField(selectedDate.get()?.let { ConversionUtils.getTimestampLongValue(it) })

}

class BibleVerseReferenceItemViewModel(initialReference: BibleReference? = null) :
    EntryItemViewModel(R.string.referenceLabel, R.layout.entry_control_item_reference) {
    val selectedReference = ObservableField<BibleReference>(initialReference)
    val referenceLabel = ObservableField(selectedReference.get()?.toString() ?: "-null-")
}

class StringItemViewModel(@StringRes label: Int, initialValue: String? = "") : EntryItemViewModel(label, R.layout.entry_control_item_string) {
    val value = ObservableField(initialValue)
}