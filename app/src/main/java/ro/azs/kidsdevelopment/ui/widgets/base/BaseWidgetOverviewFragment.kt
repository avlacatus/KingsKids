package ro.azs.kidsdevelopment.ui.widgets.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ro.azs.kidsdevelopment.base.BaseFragment
import ro.azs.kidsdevelopment.databinding.FragmentCategoryWidgetOverviewBinding

open class BaseWidgetOverviewFragment<T : BaseWidgetOverviewContract.Presenter?> : BaseFragment<T>(), BaseWidgetOverviewContract.View {

    private var _binding: FragmentCategoryWidgetOverviewBinding? = null

    protected val viewModel: BaseWidgetOverviewViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCategoryWidgetOverviewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.presenter = getPresenter()
        return binding.root
    }

    override fun displayItems(items: List<Any>) {
        viewModel.items.clear()
        viewModel.items.addAll(items)
    }
}