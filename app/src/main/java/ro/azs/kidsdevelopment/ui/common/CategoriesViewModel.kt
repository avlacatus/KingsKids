package ro.azs.kidsdevelopment.ui.categories

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ro.azs.kidsdevelopment.BR
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.models.Category
import ro.azs.kidsdevelopment.models.CategoryGroup
import java.util.function.Consumer


public class CategoriesViewModelFactory(private val listener: Consumer<Category>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoriesViewModel(listener) as T
    }
}

public class CategoriesViewModel(val listener: Consumer<Category>) : ViewModel() {

    val items: ObservableList<CategoryGroupViewModel> = ObservableArrayList()

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, item: CategoryGroupViewModel? ->
        itemBinding[BR.viewModel] = R.layout.item_home_category_group
    }
}

public class CategoryGroupViewModel(val categoryGroup: CategoryGroup, val listener: Consumer<Category>) {

    val items: ObservableList<CategoryViewModel> = ObservableArrayList()

    val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, item: CategoryViewModel? ->
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