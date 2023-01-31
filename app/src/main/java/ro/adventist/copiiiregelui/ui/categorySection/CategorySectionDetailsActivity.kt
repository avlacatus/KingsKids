package ro.adventist.copiiiregelui.ui.categorySection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import ro.adventist.copiiiregelui.R
import ro.adventist.copiiiregelui.base.BaseActivity
import ro.adventist.copiiiregelui.databinding.ActivityCategorySectionDetailsBinding
import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.models.FirestoreModel
import ro.adventist.copiiiregelui.ui.firestoreEntry.EntryDetailsActivity


@Suppress("DEPRECATION")
class CategorySectionDetailsActivity : BaseActivity<CategorySectionDetailsContract.Presenter>(), CategorySectionDetailsContract.View {

    private val viewModel: CategorySectionDetailsViewModel by viewModels {
        CategorySectionDetailsViewModelFactory(this::onItemClicked)
    }
    private val presenter by lazy { CategorySectionDetailsPresenter(this, intent.getSerializableExtra(INTENT_EXTRA_CATEGORY_SECTION) as CategorySectionType?) }
    private var _binding: ActivityCategorySectionDetailsBinding? = null

    private val binding get() = _binding!!
    private val categorySectionType by lazy {
        intent.getSerializableExtra(INTENT_EXTRA_CATEGORY_SECTION) as CategorySectionType?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ""
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_category_section_details)
        setSupportActionBar(binding.toolbar)
        binding.viewModel = viewModel
        supportActionBar?.displayOptions = ActionBar.DISPLAY_HOME_AS_UP
    }

    override fun displayItems(items: List<Any>) {
        viewModel.items.clear()
        viewModel.items.addAll(items)
    }

    override fun setupTitle(title: Int) {
        viewModel.title.set(getString(title))
    }

    private fun onItemClicked(item: FirestoreModel) {
        categorySectionType?.let {
            startActivity(EntryDetailsActivity.getIntent(this, it, item))
        }
    }

    override fun setEmptyMessage(emptyMessageRes: Int) {
        viewModel.emptyMessageRes.set(emptyMessageRes)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_add) {
            categorySectionType?.let {
                startActivity(EntryDetailsActivity.getIntent(this, it))
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getPresenter(): CategorySectionDetailsContract.Presenter = presenter

    companion object {
        private const val INTENT_EXTRA_CATEGORY_SECTION = "INTENT_EXTRA_CATEGORY_SECTION"

        fun getIntent(callingActivity: Activity, categorySection: CategorySectionType): Intent {
            val output = Intent(callingActivity, CategorySectionDetailsActivity::class.java)
            output.putExtra(INTENT_EXTRA_CATEGORY_SECTION, categorySection)
            return output
        }
    }
}