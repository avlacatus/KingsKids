package ro.azs.kidsdevelopment.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserDetailsViewModel : ViewModel() {

    val userDetails = MutableLiveData<UserProfileFormData?>()

}