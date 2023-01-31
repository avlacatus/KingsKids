package ro.adventist.copiiiregelui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ro.adventist.copiiiregelui.databinding.ActivityMainBinding
import ro.adventist.copiiiregelui.base.BaseActivity
import ro.adventist.copiiiregelui.base.BaseContract

class MainActivity : BaseActivity<BaseContract.Presenter>() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
        title = ""
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.title = destination.label?.toString() ?: ""
        }
    }

}