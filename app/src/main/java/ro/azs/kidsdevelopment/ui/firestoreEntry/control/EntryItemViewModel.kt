package ro.azs.kidsdevelopment.ui.firestoreEntry.control

import android.app.DatePickerDialog
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.google.firebase.Timestamp
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.BibleReference
import ro.azs.kidsdevelopment.models.LocalizedString
import ro.azs.kidsdevelopment.utils.ConversionUtils
import java.util.*


open class EntryItemViewModel(@StringRes val label: Int, @LayoutRes val resId: Int) {
}

open class TimestampItemViewModel(initialTimestamp: Timestamp? = null,
    private val refActivity: AppCompatActivity) : EntryItemViewModel(R.string.dateLabel, R.layout.entry_control_item_date) {
    val selectedDate = ObservableField<Timestamp>()
    val dateLabel = ObservableField("")

    val onClick = {
        val currentCalendar = Calendar.getInstance().apply {
            time = (selectedDate.get() ?: Timestamp.now()).toDate()
        }
        DatePickerDialog(
            refActivity, { _, year, month, dayOfMonth ->
                onDatePickerSet(year, month, dayOfMonth)
            }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    init {
        selectedDate.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                dateLabel.set(selectedDate.get()?.let { formatTimestamp(it) })
            }
        })
        selectedDate.set(initialTimestamp ?: Timestamp(Date()))
    }

    private fun onDatePickerSet(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        selectedDate.set(Timestamp(calendar.time))
    }

    open fun formatTimestamp(timestamp: Timestamp): String = ConversionUtils.getTimestampShortValue(timestamp)
}

class LongTimestampItemViewModel(initialTimestamp: Timestamp? = null,
    refActivity: AppCompatActivity) : TimestampItemViewModel(initialTimestamp, refActivity) {
    override fun formatTimestamp(timestamp: Timestamp): String = ConversionUtils.getTimestampLongValue(timestamp)

}

class BibleVerseReferenceItemViewModel(initialReference: BibleReference? = null,
    private val refActivity: AppCompatActivity,
    private val showVersePicker: Boolean = true) :
    EntryItemViewModel(R.string.referenceLabel,
        R.layout.entry_control_item_reference), java.io.Serializable {
    val selectedReference = ObservableField<BibleReference>()
    val referenceLabel = ObservableField("")
    val onClick = {
        val dialog = SelectBibleReferenceDialog.getDialog(selectedReference.get(), this@BibleVerseReferenceItemViewModel, showVersePicker)
        dialog.show(refActivity.supportFragmentManager, "dialog")
    }

    init {
        selectedReference.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                referenceLabel.set(selectedReference.get()?.toString() ?: "-null-")
            }
        })
        selectedReference.set(initialReference)
    }
}

class StringItemViewModel(@StringRes label: Int, initialValue: String? = "", val multiline: Boolean = false) :
    EntryItemViewModel(label, R.layout.entry_control_item_string) {
    val value = ObservableField(initialValue)
}

class NumberItemViewModel(@StringRes label: Int, initialValue: Double? = 0.0) : EntryItemViewModel(label, R.layout.entry_control_item_number) {
    val stringValue = ObservableField("")
    val value = ObservableField<Double>()

    init {
        stringValue.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                value.set(stringValue.get()?.toDoubleOrNull())
            }

        })

        stringValue.set(initialValue.toString())
    }
}

class SingleChoiceItemViewModel<T>(@StringRes label: Int, initialValue: T?, allItems: Array<T>, private val refActivity: AppCompatActivity) :
    EntryItemViewModel(label, R.layout.entry_control_item_single_choice) where T : LocalizedString {

    val selectedOption = ObservableField<T>()
    val optionLabel = ObservableField("")

    val onClick = {
        val builder: AlertDialog.Builder = AlertDialog.Builder(refActivity).apply {
            val selectedIndex = allItems.indexOfFirst { it == selectedOption.get() }
            setTitle(null)
            setSingleChoiceItems(allItems.map { it.getLocalizedString() }.toTypedArray(), selectedIndex) { dialog, index ->
                selectedOption.set(allItems[index])
                dialog.dismiss()
            }

            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }

        builder.create().show()
    }

    init {
        selectedOption.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                optionLabel.set(selectedOption.get()?.getLocalizedString() ?: "-")
            }
        })
        selectedOption.set(initialValue)
    }
}