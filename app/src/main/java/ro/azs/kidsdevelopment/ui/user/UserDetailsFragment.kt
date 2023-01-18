package ro.azs.kidsdevelopment.ui.user

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.base.BaseFragment
import ro.azs.kidsdevelopment.databinding.FragmentUserDetailsBinding
import ro.azs.kidsdevelopment.models.UserProfile
import ro.azs.kidsdevelopment.ui.user.edit.EditUserProfileActivity
import ro.azs.kidsdevelopment.utils.Logger

class UserDetailsFragment : BaseFragment<UserDetailsContract.Presenter>(),
    UserDetailsContract.View {

    private lateinit var viewModel: UserDetailsViewModel
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: UserDetailsContract.Presenter

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
            onSignInResult(res)
        }

    private val editProfileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK)
                getPresenter().onUserProfileEdited()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[UserDetailsViewModel::class.java]

        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.presenter = getPresenter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userDetails.observe(this.viewLifecycleOwner) {
            viewModel.userGreeting.set(if (it == null || it.name.value.isNullOrBlank()) getString(R.string.helloGreetingSimple) else getString(R.string.helloGreeting,
                it.name.value))
            activity?.invalidateOptionsMenu()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (viewModel.userDetails.value != null) {
            inflater.inflate(R.menu.menu_edit, menu)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_edit) {
            editProfileLauncher.launch(Intent(activity, EditUserProfileActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("RestrictedApi")
    override fun openLogIn() {
//        AuthUI.getInstance().auth.createUserWithEmailAndPassword("username", "password").addOnCompleteListener {
//            Logger.e("tag", "signup completedd")
//        }
//
//        AuthUI.getInstance().auth.signInWithEmailAndPassword("username", "password").addOnCompleteListener {
//            Logger.e("tag", "login completedd")
//        }
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.LoginTheme)
            .setLogo(R.drawable.img_profile_v2)
            .build()
        signInLauncher.launch(signInIntent)
    }

    override fun setupLoggedOutPage() {
        viewModel.userDetails.postValue(null)
    }

    override fun setupUserProfile(user: UserProfile) {
        val userViewModel =
            ViewModelProvider(this)[UserProfileFormData::class.java].initFromUserProfileData(user)
        viewModel.userDetails.postValue(userViewModel)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            getPresenter().onUserLoggedIn()
        } else {
            getPresenter().onLogInError()
        }
    }

    override fun getPresenter(): UserDetailsContract.Presenter {
        if (!this::presenter.isInitialized) {
            presenter = UserDetailsPresenter(this)
        }
        return presenter
    }

    companion object {
        private val TAG = UserDetailsFragment::class.java.simpleName
    }
}