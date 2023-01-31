package ro.adventist.copiiiregelui.ui.user

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserDetailsViewModel : ViewModel() {

    val userGreeting = ObservableField("")
    val userDetails = MutableLiveData<UserProfileFormData?>()
}