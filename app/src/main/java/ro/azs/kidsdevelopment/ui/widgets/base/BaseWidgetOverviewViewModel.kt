package ro.azs.kidsdevelopment.ui.widgets.base

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ro.azs.kidsdevelopment.BR
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.BibleDiscovery
import ro.azs.kidsdevelopment.models.BibleFavoriteText
import ro.azs.kidsdevelopment.models.BiblePrayerText

class BaseWidgetOverviewViewModel : ViewModel() {

    val titleRes = ObservableField(-1)
    val hintRes = ObservableField(-1)
    val labelAddRes = ObservableField(-1)
    val isAddLabelVisible = ObservableBoolean(true)
    val items = ObservableArrayList<Any>()

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, item: Any? ->
        if (item is BibleDiscovery) {
            itemBinding[BR.viewModel] = R.layout.item_list_bible_discovery
        } else if (item is BibleFavoriteText) {
            itemBinding[BR.viewModel] = R.layout.item_list_bible_favorite_text
        } else if (item is BiblePrayerText) {
            itemBinding[BR.viewModel] = R.layout.item_list_bible_prayer_text
        }
    }


}