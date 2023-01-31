package ro.azs.kidsdevelopment.ui.firestoreEntry.control

import android.app.DatePickerDialog
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Observable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.google.firebase.Timestamp
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ro.azs.kidsdevelopment.BR
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.BibleReference
import ro.azs.kidsdevelopment.models.LocalizedString
import ro.azs.kidsdevelopment.models.PrayerSubjectAnswer
import ro.azs.kidsdevelopment.utils.ConversionUtils
import ro.azs.kidsdevelopment.utils.Logger
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
                value.set(stringValue.get()?.toDoubleOrNull() ?: 0.0)
            }
        })

        stringValue.set((initialValue ?: 0.0).toString())
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

class RadioChoiceItemViewModel<T>(@StringRes label: Int, initialValue: T?, allItems: Array<T>) :
    EntryItemViewModel(label, R.layout.entry_control_item_radio_choice) where T : LocalizedString {

    val selectedOption = ObservableField<T>()
    val items = ObservableArrayList<RadioItemVM>()
    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, _: RadioItemVM? ->
        itemBinding[BR.viewModel] = R.layout.item_list_generic_radio
    }

    init {
        items.addAll(allItems.map { item ->
            RadioItemVM(item).apply {
                isSelected.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                        if (this@apply.isSelected.get()) {
                            selectedOption.set(item)
                            items.forEach { otherVm ->
                                if (otherVm != this@apply) {
                                    otherVm.isSelected.set(false)
                                }
                            }
                        } else {
                            if (item == selectedOption.get()) {
                                isSelected.set(true)
                                Logger.e("errrrooorrr", "s-a dezactivat singurul element selectat")
                            }
                        }
                    }

                })
                isSelected.set(item == initialValue)
            }
        })
    }

    class RadioItemVM(val item: LocalizedString) {
        val label = item.getLocalizedString()
        val isSelected = ObservableBoolean(false)

    }
}


class PrayerAnswerItemViewModel(initialValue: PrayerSubjectAnswer?, private val refActivity: AppCompatActivity) :
    EntryItemViewModel(R.string.answerLabel, R.layout.entry_control_item_prayer_answer) {

    val isAnswered = ObservableBoolean()
    val answerDate = ObservableField<Timestamp>()
    val answerDescription = ObservableField<String>()


    val onDateClick = {
        val currentCalendar = Calendar.getInstance().apply {
            time = (answerDate.get() ?: Timestamp.now()).toDate()
        }
        DatePickerDialog(
            refActivity, { _, year, month, dayOfMonth ->
                onDatePickerSet(year, month, dayOfMonth)
            }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun onDatePickerSet(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        answerDate.set(Timestamp(calendar.time))
    }


    init {
        initialValue?.let {
            isAnswered.set(true)
            answerDate.set(it.date)
            answerDescription.set(it.description)
        } ?: kotlin.run {
            isAnswered.set(false)
            answerDate.set(Timestamp.now())
            answerDescription.set("")
        }
    }
}