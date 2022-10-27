package ro.azs.kidsdevelopment.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ro.azs.kidsdevelopment.base.BaseFragment
import ro.azs.kidsdevelopment.databinding.FragmentCategoriesBinding
import ro.azs.kidsdevelopment.models.Category
import ro.azs.kidsdevelopment.models.CategoryGroup
import ro.azs.kidsdevelopment.ui.details.CategoryDetailsActivity
import ro.azs.kidsdevelopment.utils.Logger

class CategoriesFragment : BaseFragment<CategoriesContract.Presenter>(), CategoriesContract.View {

    private val viewModel: CategoriesViewModel by viewModels { CategoriesViewModelFactory { presenter.onCategorySelected(it) } }
    private lateinit var presenter: CategoriesContract.Presenter
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

    override fun displaySections(groups: List<CategoryGroup>, favourites: List<Category>) {
        Logger.e("CategoriesFragment", "displaySections called with ${groups.size}")
        viewModel.items.clear()
        viewModel.items.addAll(groups.withIndex().map { (index, group) ->
            CategoryGroupViewModel(group) { presenter.onCategorySelected(it) }.apply {
                showBottomMargin.set(index == groups.size - 1)
            }
        })
    }

    override fun openCategoryDetails(category: Category) {
        startActivity(CategoryDetailsActivity.getIntent(requireActivity(), category.type.categoryTypeId))
    }

    override fun getPresenter(): CategoriesContract.Presenter {
        if (!this::presenter.isInitialized) {
            presenter = CategoriesPresenter(this)
        }
        return presenter
    }

    companion object {
        private val TAG = CategoriesFragment::class.java.simpleName
    }
}