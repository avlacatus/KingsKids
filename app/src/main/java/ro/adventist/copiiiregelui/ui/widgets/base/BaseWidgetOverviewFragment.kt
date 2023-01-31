package ro.adventist.copiiiregelui.ui.widgets.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ro.adventist.copiiiregelui.base.BaseFragment
import ro.adventist.copiiiregelui.databinding.FragmentCategoryWidgetOverviewBinding
import ro.adventist.copiiiregelui.models.FirestoreModel
import ro.adventist.copiiiregelui.ui.categorySection.CategorySectionDetailsActivity
import ro.adventist.copiiiregelui.ui.firestoreEntry.EntryDetailsActivity

abstract class BaseWidgetOverviewFragment<T : BaseWidgetOverviewContract.Presenter> : BaseFragment<T>(), BaseWidgetOverviewContract.View {

    private var _binding: FragmentCategoryWidgetOverviewBinding? = null

    protected val viewModel: BaseWidgetOverviewViewModel by viewModels {
        BaseWidgetOverviewViewModelFactory { getPresenter()?.onItemClicked(it) }
    }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCategoryWidgetOverviewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.presenter = getPresenter()

        getCategorySectionType().let { sectionType ->
            viewModel.titleRes.set(sectionType.titleRes)
            viewModel.hintRes.set(sectionType.hintRes)
            viewModel.emptyMessageRes.set(sectionType.emptyRes)
            viewModel.labelAddRes.set(sectionType.addLabelRes)
        }

        return binding.root
    }

    override fun openCategorySectionDetails() {
        startActivity(CategorySectionDetailsActivity.getIntent(requireActivity(), getCategorySectionType()))
    }

    override fun openEditEntry(model: FirestoreModel) {
        startActivity(EntryDetailsActivity.getIntent(requireActivity(), getCategorySectionType(), model))
    }

    override fun openNewEntry() {
        startActivity(EntryDetailsActivity.getIntent(requireActivity(), getCategorySectionType()))
    }

    override fun displayItems(items: List<Any>) {
        viewModel.items.clear()
        viewModel.items.addAll(items)
    }
}