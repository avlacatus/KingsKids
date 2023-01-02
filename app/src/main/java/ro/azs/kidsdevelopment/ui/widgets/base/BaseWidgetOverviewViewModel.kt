package ro.azs.kidsdevelopment.ui.widgets.base

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ro.azs.kidsdevelopment.BR
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.*
import java.util.function.Consumer


class BaseWidgetOverviewViewModelFactory(listener: Consumer<FirestoreModel>) : ViewModelProvider.Factory {
    private val itemClickListener: Consumer<FirestoreModel>

    init {
        itemClickListener = listener
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BaseWidgetOverviewViewModel(itemClickListener) as T
    }

}

class BaseWidgetOverviewViewModel(itemClickListener: Consumer<FirestoreModel>) : ViewModel() {

    val titleRes = ObservableField(-1)
    val hintRes = ObservableField(-1)
    val emptyMessageRes = ObservableField(-1)
    val labelAddRes = ObservableField(-1)
    val isAddLabelVisible = ObservableBoolean(true)
    val items = ObservableArrayList<Any>()

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, item: Any? ->
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
                itemBinding[BR.viewModel] = R.layout.item_list_widget_timestamp_label
            }
        }
    }.bindExtra(BR.listener, itemClickListener)
        .bindExtra(BR.useDarkSeparator, true)


}