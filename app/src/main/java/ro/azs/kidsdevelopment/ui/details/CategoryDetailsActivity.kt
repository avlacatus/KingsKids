package ro.azs.kidsdevelopment.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.base.BaseActivity
import ro.azs.kidsdevelopment.databinding.ActivityCategoryDetailsBinding
import ro.azs.kidsdevelopment.models.CategoryType
import ro.azs.kidsdevelopment.ui.widgets.WidgetHelper


class CategoryDetailsActivity : BaseActivity<CategoryDetailsContract.Presenter>(), CategoryDetailsContract.View {

    private val viewModel by lazy { ViewModelProvider(this)[CategoryDetailsViewModel::class.java] }
    private val presenter by lazy { CategoryDetailsPresenter(this, intent.getStringExtra(INTENT_EXTRA_CATEGORY_ID) ?: "") }
    private var _binding: ActivityCategoryDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ""
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_category_details)

        setSupportActionBar(binding.toolbar)
        binding.viewModel = viewModel
        supportActionBar?.displayOptions = ActionBar.DISPLAY_HOME_AS_UP

        viewModel.isFavoriteVisible.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                invalidateOptionsMenu()
            }
        })

        viewModel.isMarkedFavorite.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                invalidateOptionsMenu()
            }
        })

        viewModel.areWidgetsVisible.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (viewModel.areWidgetsVisible.get()) {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    supportFragmentManager.fragments.forEach { ft.remove(it) }

                    val categoryType = CategoryType.getCategoryById(intent.getStringExtra(INTENT_EXTRA_CATEGORY_ID) ?: "")
                    val widgetsForCategory = WidgetHelper.getWidgetsForCategory(categoryType)

                    if (widgetsForCategory.isNotEmpty()) {
                        for (fragmentClass in widgetsForCategory) {
                            ft.add(R.id.cd_widget_holder, fragmentClass.newInstance() as Fragment, fragmentClass.name)
                        }
                    }
                    ft.commit()
                } else {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    supportFragmentManager.fragments.forEach { ft.remove(it) }
                    ft.commit()
                }
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        return if (viewModel.isFavoriteVisible.get()) {
            if (viewModel.isMarkedFavorite.get()) {
                menuInflater.inflate(R.menu.menu_unmark_favorite, menu)
            } else {
                menuInflater.inflate(R.menu.menu_mark_favorite, menu)
            }
            true
        } else {
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_mark_favorite) {
            presenter.onFavouriteClicked(true)
            return true
        } else if (item.itemId == R.id.menu_item_unmark_favorite) {
            presenter.onFavouriteClicked(false)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun displayDetails(titleRes: Int, iconRes: Int, headerRes: Int, messageRes: Int) {
        viewModel.title.set(getString(titleRes))
        viewModel.headerRes.set(headerRes)
        viewModel.iconRes.set(iconRes)
        viewModel.messageRes.set(messageRes)
    }

    override fun setIsFavorite(isFavourite: Boolean) {
        viewModel.isMarkedFavorite.set(isFavourite)
    }

    override fun setFavoriteVisible(isVisible: Boolean) {
        viewModel.isFavoriteVisible.set(isVisible)
    }

    override fun setupLoggedOutView() {
        viewModel.areWidgetsVisible.set(false)
    }

    override fun setupWidgets() {
        viewModel.areWidgetsVisible.set(true)
    }

    override fun getPresenter(): CategoryDetailsContract.Presenter  = presenter

    companion object {
        private const val INTENT_EXTRA_CATEGORY_ID = "INTENT_EXTRA_CATEGORY_ID"

        fun getIntent(callingActivity: Activity, categoryId: String): Intent {
            val output = Intent(callingActivity, CategoryDetailsActivity::class.java)
            output.putExtra(INTENT_EXTRA_CATEGORY_ID, categoryId)
            return output
        }
    }
}