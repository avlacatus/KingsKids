package ro.azs.kidsdevelopment.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.base.BaseFragment
import ro.azs.kidsdevelopment.databinding.FragmentCategoriesBinding
import ro.azs.kidsdevelopment.models.Category
import ro.azs.kidsdevelopment.models.CategoryGroup
import ro.azs.kidsdevelopment.ui.categories.CategoriesViewModel
import ro.azs.kidsdevelopment.ui.categories.CategoriesViewModelFactory
import ro.azs.kidsdevelopment.ui.categories.CategoryGroupViewModel
import ro.azs.kidsdevelopment.ui.details.CategoryDetailsActivity

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

    override fun getPresenter(): FavoritesContract.Presenter {
        return presenter
    }

    companion object {
        private val TAG = FavoritesFragment::class.java.simpleName
    }
}