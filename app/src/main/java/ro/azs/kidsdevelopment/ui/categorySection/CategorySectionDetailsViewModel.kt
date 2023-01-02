package ro.azs.kidsdevelopment.ui.categorySection

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ro.azs.kidsdevelopment.BR
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.*
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewViewModel
import java.util.function.Consumer

class CategorySectionDetailsViewModelFactory(listener: Consumer<FirestoreModel>) : ViewModelProvider.Factory {
    private val itemClickListener: Consumer<FirestoreModel>

    init {
        itemClickListener = listener
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategorySectionDetailsViewModel(itemClickListener) as T
    }

}

class CategorySectionDetailsViewModel(itemClickListener: Consumer<FirestoreModel>) : ViewModel() {
    val title = ObservableField("")

    val emptyMessageRes = ObservableField(-1)
    val items = ObservableArrayList<Any>()

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, position: Int, item: Any? ->
        when (item) {
            is BodyRoutine -> {
                itemBinding[BR.viewModel] = R.layout.item_list_body_routine
            }
            is DailySchedule -> {
                itemBinding[BR.viewModel] = R.layout.item_list_daily_schedule
            }
            is HealthMetric -> {
                itemBinding[BR.viewModel] = R.layout.item_list_health_metric
            }
            is WithDescriptionTimestamp -> {
                itemBinding[BR.viewModel] = R.layout.item_list_long_label_timestamp
            }
        }
    }.bindExtra(BR.listener, itemClickListener)
        .bindExtra(BR.useDarkSeparator, false)
}