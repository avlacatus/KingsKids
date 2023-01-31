package ro.adventist.copiiiregelui.ui.categories

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import ro.adventist.copiiiregelui.BR
import ro.adventist.copiiiregelui.R
import ro.adventist.copiiiregelui.models.Category
import ro.adventist.copiiiregelui.models.CategoryGroup
import java.util.function.Consumer


class CategoriesViewModelFactory(private val listener: Consumer<Category>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoriesViewModel(listener) as T
    }
}

class CategoriesViewModel(val listener: Consumer<Category>) : ViewModel() {

    private val diffCallback: DiffUtil.ItemCallback<CategoryGroupViewModel> = object : DiffUtil.ItemCallback<CategoryGroupViewModel>() {
        override fun areItemsTheSame(oldItem: CategoryGroupViewModel, newItem: CategoryGroupViewModel): Boolean {
            return oldItem.categoryGroup == newItem.categoryGroup
        }

        override fun areContentsTheSame(oldItem: CategoryGroupViewModel, newItem: CategoryGroupViewModel): Boolean {
            return oldItem.items.containsAll(newItem.items) && newItem.items.containsAll(oldItem.items)
        }
    }
    val items: DiffObservableList<CategoryGroupViewModel> = DiffObservableList(diffCallback)

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, _: CategoryGroupViewModel? ->
        itemBinding[BR.viewModel] = R.layout.item_home_category_group
    }

    val emptyMessage = ObservableField("")
}

class CategoryGroupViewModel(val categoryGroup: CategoryGroup, val listener: Consumer<Category>) {

    val items: ObservableList<CategoryViewModel> = ObservableArrayList()

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, _: CategoryViewModel? ->
        itemBinding[BR.viewModel] = R.layout.item_home_category
        itemBinding.bindExtra(BR.listener, listener)
    }

    val showBottomMargin = ObservableBoolean(false)

    init {
        items.addAll(categoryGroup.categories.withIndex().map { (index, value) ->
            CategoryViewModel(value, listener).apply {
                showStartMargin.set(index == 0)
                showEndMargin.set(index == categoryGroup.categories.size - 1)
            }
        })
    }
}

public class CategoryViewModel(val category: Category, val listener: Consumer<Category>) {
    val itemCountDesc = ObservableField("")
    val showStartMargin = ObservableBoolean(false)
    val showEndMargin = ObservableBoolean(false)
}