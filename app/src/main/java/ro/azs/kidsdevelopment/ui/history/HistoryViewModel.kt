package ro.azs.kidsdevelopment.ui.history

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import ro.azs.kidsdevelopment.BR
import ro.azs.kidsdevelopment.R

class HistoryViewModel : ViewModel() {

    val message = ObservableField("")

    private val diffCallback: DiffUtil.ItemCallback<HistoryEntryViewModel> = object : DiffUtil.ItemCallback<HistoryEntryViewModel>() {
        override fun areItemsTheSame(oldItem: HistoryEntryViewModel, newItem: HistoryEntryViewModel): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: HistoryEntryViewModel, newItem: HistoryEntryViewModel): Boolean {
            return oldItem.description == newItem.description
        }
    }
    val items = DiffObservableList(diffCallback)

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, _: HistoryEntryViewModel? ->
        itemBinding[BR.viewModel] = R.layout.item_list_history_entry
    }
}