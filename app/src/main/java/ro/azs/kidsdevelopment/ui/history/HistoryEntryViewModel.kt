package ro.azs.kidsdevelopment.ui.history

import androidx.databinding.ObservableBoolean
import ro.azs.kidsdevelopment.R

data class HistoryEntryViewModel(
    val description: String = "",
    val timestamp: String = "",
    val drawableRes: Int = R.drawable.ic_history_add,
    val showBottomMargin:ObservableBoolean = ObservableBoolean(false)

)