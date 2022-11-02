package ro.azs.kidsdevelopment.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.base.BaseFragment
import ro.azs.kidsdevelopment.databinding.FragmentHomeBinding
import ro.azs.kidsdevelopment.models.CategoryType
import ro.azs.kidsdevelopment.ui.user.edit.EditUserProfileActivity
import ro.azs.kidsdevelopment.ui.widgets.WidgetHelper

@Deprecated("not implemented in v1")
class HomeFragment : BaseFragment<HomeContract.Presenter>(), HomeContract.View {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var presenter: HomeContract.Presenter
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
//            onSignInResult(res)
        }

    private val editProfileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
//            if (activityResult.resultCode == Activity.RESULT_OK)
//                getPresenter().onUserProfileEdited()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.isLoggedIn.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                activity?.invalidateOptionsMenu()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (viewModel.isLoggedIn.get()) {
            inflater.inflate(R.menu.menu_home, menu)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_user_profile) {
            editProfileLauncher.launch(Intent(activity, EditUserProfileActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openLogIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    override fun displayUserData(name: String, categories: List<CategoryType>) {
        viewModel.isLoggedIn.set(true)
        viewModel.userGreeting.set("Buna, $name!")

        if (!viewModel.favoriteCategories.containsAll(categories) || !categories.containsAll(viewModel.favoriteCategories)) {

            viewModel.favoriteCategories.clear()
            viewModel.favoriteCategories.addAll(categories)

            for (fragment in childFragmentManager.fragments) {
                childFragmentManager.beginTransaction().remove(fragment).commit()
            }
            val ft = childFragmentManager.beginTransaction()
            categories.forEach {
                val widgetsForCategory = WidgetHelper.getWidgetsForCategory(it)
                if (widgetsForCategory.isNotEmpty()) {
                    for (fragmentClass in widgetsForCategory) {
                        ft.add(R.id.cd_widget_holder, fragmentClass.newInstance() as Fragment, fragmentClass.name)
                    }
                }
            }
            ft.commit()
        }
    }

    override fun setupLoggedOutView() {
        viewModel.isLoggedIn.set(false)
        val ft: FragmentTransaction = childFragmentManager.beginTransaction()
        childFragmentManager.fragments.forEach { ft.remove(it) }
        ft.commit()
    }

//    override fun displaySections(groups: List<CategoryGroup>, favourites: List<Category>) {
//        viewModel.items.clear()
//
//        if (favourites.isNotEmpty()) {
//            viewModel.items.add(CategoryGroup(CategoryGroupType.favorites, 0, favourites))
//            viewModel.items.addAll(favourites)
//        }
//        for (section in groups) {
//            val groupSections = section.categories.minus(favourites.toSet())
//            if (groupSections.isNotEmpty()) {
//                viewModel.items.add(section)
//                viewModel.items.addAll(groupSections)
//            }
//        }
//
//        val layoutManager = (binding.recyclerView.layoutManager as GridLayoutManager)
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return if (viewModel.items[position] is CategoryGroup) layoutManager.spanCount else 1
//            }
//        }
//    }

//    override fun openCategoryDetails(category: Category) {
//        startActivity(CategoryDetailsActivity.getIntent(requireActivity(), category.type.categoryTypeId))
//    }

    override fun getPresenter(): HomeContract.Presenter {
        if (!this::presenter.isInitialized) {
            presenter = HomePresenter(this)
        }
        return presenter
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }
}