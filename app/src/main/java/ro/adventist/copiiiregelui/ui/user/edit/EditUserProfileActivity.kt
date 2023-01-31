package ro.adventist.copiiiregelui.ui.user.edit

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ro.adventist.copiiiregelui.R
import ro.adventist.copiiiregelui.base.BaseActivity
import ro.adventist.copiiiregelui.databinding.ActivityEditUserProfileBinding
import ro.adventist.copiiiregelui.models.UserProfile
import ro.adventist.copiiiregelui.ui.user.UserProfileFormData

class EditUserProfileActivity : BaseActivity<EditUserProfileContract.Presenter?>(),
    EditUserProfileContract.View {
    private lateinit var binding: ActivityEditUserProfileBinding
    private lateinit var viewModel: UserProfileFormData
    private lateinit var presenter: EditUserProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this).get(UserProfileFormData::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user_profile)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        title = ""
        setSupportActionBar(binding.toolbar)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_HOME_AS_UP
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_save) {
            getPresenter().onSaveClicked()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setupUserDetailsPage(user: UserProfile) {
        viewModel.initFromUserProfileData(user)
    }


    override fun getUserProfileInput(): UserProfile {
        return UserProfile(
            viewModel.name.value ?: "",
            viewModel.parents.value ?: "",
            viewModel.church.value ?: "",
            viewModel.coach.value ?: "",
            viewModel.pastor.value ?: "",
            viewModel.school.value ?: "",
            viewModel.grade.value ?: ""
        )
    }

    override fun closeWithOk() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun getPresenter(): EditUserProfileContract.Presenter {
        if (!this::presenter.isInitialized) {
            presenter = EditUserProfilePresenter(this)
        }
        return presenter
    }
}