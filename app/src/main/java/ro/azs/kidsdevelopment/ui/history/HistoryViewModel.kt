package ro.azs.kidsdevelopment.ui.history

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ro.azs.kidsdevelopment.BR
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.BibleDiscovery
import ro.azs.kidsdevelopment.models.BibleFavoriteText
import ro.azs.kidsdevelopment.models.BiblePrayerText

class HistoryViewModel : ViewModel() {

    val items = ObservableArrayList<Any>()

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, position: Int, item: Any? ->
        if (item is BibleDiscovery) {
            itemBinding[BR.viewModel] = R.layout.item_list_bible_discovery
        } else if (item is BibleFavoriteText) {
            itemBinding[BR.viewModel] = R.layout.item_list_bible_favorite_text
        } else if (item is BiblePrayerText) {
            itemBinding[BR.viewModel] = R.layout.item_list_bible_prayer_text
        }
    }
}