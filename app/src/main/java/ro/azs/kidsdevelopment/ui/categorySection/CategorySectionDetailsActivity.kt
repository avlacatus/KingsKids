package ro.azs.kidsdevelopment.ui.categorySection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.base.BaseActivity
import ro.azs.kidsdevelopment.databinding.ActivityCategoryDetailsBinding
import ro.azs.kidsdevelopment.databinding.ActivityCategorySectionDetailsBinding
import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.models.FirestoreModel
import ro.azs.kidsdevelopment.ui.firestoreEntry.EntryDetailsActivity
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewViewModelFactory


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