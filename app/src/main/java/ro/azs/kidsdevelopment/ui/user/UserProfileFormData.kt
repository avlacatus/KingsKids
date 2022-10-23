package ro.azs.kidsdevelopment.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ro.azs.kidsdevelopment.models.UserProfile

class UserProfileFormData : ViewModel() {

    val name = MutableLiveData<String>()
    val parents = MutableLiveData<String>()
    val church = MutableLiveData<String>()
    val coach = MutableLiveData<String>()
    val pastor = MutableLiveData<String>()
    val school = MutableLiveData<String>()
    val grade = MutableLiveData<String>()


    fun initFromUserProfileData(userProfileData: UserProfile?): UserProfileFormData {
        name.postValue(userProfileData?.name ?: "")
        parents.postValue(userProfileData?.parents ?: "")
        church.postValue(userProfileData?.church ?: "")
        coach.postValue(userProfileData?.coach ?: "")
        pastor.postValue(userProfileData?.pastor ?: "")
        school.postValue(userProfileData?.school ?: "")
        grade.postValue(userProfileData?.grade ?: "")
        return this
    }
}