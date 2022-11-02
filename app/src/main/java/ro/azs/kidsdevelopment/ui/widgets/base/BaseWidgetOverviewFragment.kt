package ro.azs.kidsdevelopment.ui.widgets.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.base.BaseFragment
import ro.azs.kidsdevelopment.databinding.FragmentCategoryWidgetOverviewBinding
import ro.azs.kidsdevelopment.models.CategorySectionType

abstract class BaseWidgetOverviewFragment<T : BaseWidgetOverviewContract.Presenter> : BaseFragment<T>(), BaseWidgetOverviewContract.View {

    private var _binding: FragmentCategoryWidgetOverviewBinding? = null

    protected val viewModel: BaseWidgetOverviewViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCategoryWidgetOverviewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.presenter = getPresenter()

        getCategorySectionType().let { sectionType ->
            viewModel.titleRes.set(sectionType.widgetTitleRes)
            viewModel.hintRes.set(sectionType.widgetHintRes)
            viewModel.emptyMessageRes.set(sectionType.widgetEmptyRes)
            viewModel.labelAddRes.set(sectionType.widgetAddLabelRes)
        }

        return binding.root
    }

    override fun displayItems(items: List<Any>) {
        viewModel.items.clear()
        viewModel.items.addAll(items)
    }
}