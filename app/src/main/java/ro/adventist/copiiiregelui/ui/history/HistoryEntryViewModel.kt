package ro.adventist.copiiiregelui.ui.history

import androidx.databinding.ObservableBoolean
import ro.adventist.copiiiregelui.R

data class HistoryEntryViewModel(
    val description: String = "",
    val timestamp: String = "",
    val drawableRes: Int = R.drawable.ic_history_add,
    val showBottomMargin:ObservableBoolean = ObservableBoolean(false)

)