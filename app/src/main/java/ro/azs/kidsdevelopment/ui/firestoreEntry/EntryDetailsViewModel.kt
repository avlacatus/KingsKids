package ro.azs.kidsdevelopment.ui.firestoreEntry

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.databinding.ObservableMap
import androidx.lifecycle.ViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ro.azs.kidsdevelopment.BR
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.ui.firestoreEntry.control.BibleVerseReferenceItemViewModel
import ro.azs.kidsdevelopment.ui.firestoreEntry.control.EntryItemViewModel
import ro.azs.kidsdevelopment.ui.firestoreEntry.control.StringItemViewModel
import ro.azs.kidsdevelopment.ui.firestoreEntry.control.LongTimestampItemViewModel
import java.security.KeyStore.Entry

class EntryDetailsViewModel : ViewModel() {

    val title = ObservableField("")

    private val itemsMap = LinkedHashMap<String, EntryItemViewModel>()

    val items: ObservableList< EntryItemViewModel> = ObservableArrayList()

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, item: EntryItemViewModel? ->
        itemBinding[BR.viewModel] = item?.resId ?: R.layout.entry_control_item_string
    }


    fun setItemsMap(map: LinkedHashMap<String, EntryItemViewModel>) {
        itemsMap.clear()
        itemsMap.putAll(map)

        items.clear()
        items.addAll(map.values)
    }

    fun getItemForTag(tag: String): EntryItemViewModel? {
        return itemsMap[tag]
    }

}