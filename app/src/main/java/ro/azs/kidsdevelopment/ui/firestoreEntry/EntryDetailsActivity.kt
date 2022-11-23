package ro.azs.kidsdevelopment.ui.firestoreEntry

import android.app.Activity
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.base.BaseActivity
import ro.azs.kidsdevelopment.databinding.ActivityCategoryDetailsBinding
import ro.azs.kidsdevelopment.databinding.ActivityEntryDetailsBinding
import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.models.FirestoreModel
import ro.azs.kidsdevelopment.ui.details.CategoryDetailsViewModel


@Suppress("DEPRECATION")
class EntryDetailsActivity : BaseActivity<EntryDetailsContract.Presenter>(), EntryDetailsContract.View {

    private val viewModel by lazy { ViewModelProvider(this)[EntryDetailsViewModel::class.java] }
    private val presenter by lazy {
        val categorySectionType = intent.getSerializableExtra(INTENT_EXTRA_CATEGORY_SECTION_TYPE) as CategorySectionType
        val existingModel = (intent.getParcelableExtra(INTENT_EXTRA_EXISTING_MODEL) as FirestoreModel?)?.apply {
            id = intent.getStringExtra(INTENT_EXTRA_EXISTING_MODEL_ID)
        }

        EntryDetailsPresenter(this, categorySectionType, existingModel)
    }
    private var _binding: ActivityEntryDetailsBinding? = null

    private val binding get() = _binding!!

    private var showDeleteOption: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ""
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_entry_details)
        setSupportActionBar(binding.toolbar)
        binding.viewModel = viewModel
        supportActionBar?.displayOptions = ActionBar.DISPLAY_HOME_AS_UP
    }

    override fun setMsg(msg: String) {
        viewModel.msg.set(msg)
    }

    override fun setupTitle(titleRes: Int) {
        viewModel.title.set(getString(titleRes))
    }

    override fun setShowDeleteOption(show: Boolean) {
        this.showDeleteOption = show
        invalidateMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (showDeleteOption) {
            menuInflater.inflate(R.menu.menu_delete, menu)
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_delete) {
            presenter.onDeleteButtonClicked()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun closeScreen() {
        finish()
    }

    override fun getPresenter(): EntryDetailsContract.Presenter = presenter

    companion object {
        private const val INTENT_EXTRA_CATEGORY_SECTION_TYPE = "INTENT_EXTRA_CATEGORY_SECTION_TYPE"
        private const val INTENT_EXTRA_EXISTING_MODEL = "INTENT_EXTRA_EXISTING_MODEL"
        private const val INTENT_EXTRA_EXISTING_MODEL_ID = "INTENT_EXTRA_EXISTING_MODEL_ID"

        fun getIntent(callingActivity: Activity, categorySection: CategorySectionType, existingModel: FirestoreModel? = null): Intent {
            val output = Intent(callingActivity, EntryDetailsActivity::class.java)
            output.putExtra(INTENT_EXTRA_CATEGORY_SECTION_TYPE, categorySection)
            output.putExtra(INTENT_EXTRA_EXISTING_MODEL, existingModel)
            output.putExtra(INTENT_EXTRA_EXISTING_MODEL_ID, existingModel?.id)
            return output
        }
    }
}