package ro.azs.kidsdevelopment.ui.details

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class CategoryDetailsViewModel : ViewModel() {

    val title = ObservableField("")
    val iconRes = ObservableField(-1)
    val headerRes = ObservableField(-1)
    val messageRes = ObservableField(-1)
    val isFavoriteVisible = ObservableBoolean()
    val isMarkedFavorite = ObservableBoolean()
    val areWidgetsVisible = ObservableBoolean()

}