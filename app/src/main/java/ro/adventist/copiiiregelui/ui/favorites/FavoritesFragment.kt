package ro.adventist.copiiiregelui.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ro.adventist.copiiiregelui.R
import ro.adventist.copiiiregelui.base.BaseFragment
import ro.adventist.copiiiregelui.databinding.FragmentCategoriesBinding
import ro.adventist.copiiiregelui.models.Category
import ro.adventist.copiiiregelui.models.CategoryGroup
import ro.adventist.copiiiregelui.ui.categories.CategoriesViewModel
import ro.adventist.copiiiregelui.ui.categories.CategoriesViewModelFactory
import ro.adventist.copiiiregelui.ui.categories.CategoryGroupViewModel
import ro.adventist.copiiiregelui.ui.details.CategoryDetailsActivity

class FavoritesFragment : BaseFragment<FavoritesContract.Presenter>(), FavoritesContract.View {

    private val viewModel: CategoriesViewModel by viewModels { CategoriesViewModelFactory { presenter.onCategorySelected(it) } }
    private val presenter by lazy { FavoritesPresenter(this) }
    private var _binding: FragmentCategoriesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun displaySections(categories: List<CategoryGroup>) {
        viewModel.items.update(categories.withIndex().map { (index, group) ->
            CategoryGroupViewModel(group) { presenter.onCategorySelected(it) }.apply {
                showBottomMargin.set(index == categories.size - 1)
            }
        })

        viewModel.emptyMessage.set(if (categories.isEmpty()) getString(R.string.emptyFavoritesMessage) else "")
    }

    override fun openCategoryDetails(category: Category) {
        startActivity(CategoryDetailsActivity.getIntent(requireActivity(), category.type.categoryTypeId))
    }

    override fun setupLoggedOutView() {
        viewModel.items.clear()
        viewModel.emptyMessage.set(getString(R.string.notAuthenticatedHomeMessage))
    }

    override fun getPresenter(): FavoritesContract.Presenter {
        return presenter
    }

    companion object {
        private val TAG = FavoritesFragment::class.java.simpleName
    }
}