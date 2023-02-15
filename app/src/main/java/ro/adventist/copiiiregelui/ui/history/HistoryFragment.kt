package ro.adventist.copiiiregelui.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ro.adventist.copiiiregelui.R
import ro.adventist.copiiiregelui.base.BaseFragment
import ro.adventist.copiiiregelui.databinding.FragmentHistoryBinding
import ro.adventist.copiiiregelui.models.HistoryEventType
import ro.adventist.copiiiregelui.models.HistoryItem
import ro.adventist.copiiiregelui.utils.ConversionUtils

class HistoryFragment : BaseFragment<HistoryContract.Presenter>(),
    HistoryContract.View {

    private val viewModel by lazy { ViewModelProvider(this)[HistoryViewModel::class.java] }
    private lateinit var presenter: HistoryContract.Presenter
    private var _binding: FragmentHistoryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun displayHistoryItems(items: List<HistoryItem>) {
        if (items.isNotEmpty()) {
            viewModel.message.set("")
            viewModel.items.update(items.withIndex().map { (index, it) ->
                HistoryEntryViewModel(getHistoryItemDescription(it),
                    ConversionUtils.getTimestampShortValue(it.date),
                    when (it.eventType) {
                        HistoryEventType.deleted -> R.drawable.ic_history_delete
                        HistoryEventType.created -> R.drawable.ic_history_add
                        HistoryEventType.updated -> R.drawable.ic_history_update
                    }).apply {
                    showBottomMargin.set(index == items.size - 1)
                }
            })
        } else {
            viewModel.message.set(getString(R.string.historyListEmptyMessage))
        }
    }

    private fun getHistoryItemDescription(item: HistoryItem): String {
        return when (item.eventType) {
            HistoryEventType.deleted -> getString(R.string.oneItemFromSection, getString(R.string.deleted), getString(item.type.titleRes))
            HistoryEventType.created -> getString(R.string.oneItemInSection, getString(R.string.created), getString(item.type.titleRes))
            HistoryEventType.updated -> getString(R.string.oneItemFromSection, getString(R.string.updated), getString(item.type.titleRes))
        }
    }

    override fun getPresenter(): HistoryContract.Presenter {
        if (!this::presenter.isInitialized) {
            presenter = HistoryPresenter(this)
        }
        return presenter
    }

    override fun setupLoggedOutView() {
        viewModel.items.clear()
        viewModel.message.set(getString(R.string.notAuthenticatedHomeMessage))
    }
}