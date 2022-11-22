package ro.azs.kidsdevelopment.ui.firestoreEntry

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class EntryDetailsViewModel : ViewModel() {

    val title = ObservableField("")
    val msg = ObservableField("")

}