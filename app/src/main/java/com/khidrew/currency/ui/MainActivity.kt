package com.khidrew.currency.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.khidrew.currency.R
import com.khidrew.currency.databinding.ActivityMainBinding
import com.khidrew.currency.utils.NetworkReceiver
import com.khidrew.currency.viewModels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var networkChangeReceiver:NetworkReceiver
    private val viewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUI()
        registerConnectionReceiver()
    }

    private fun registerConnectionReceiver() {
        networkChangeReceiver = NetworkReceiver { isNetworkEnabled ->
            handleNetworkChange(isNetworkEnabled)
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)


        registerReceiver(networkChangeReceiver, intentFilter)
    }

    private fun handleNetworkChange(networkEnabled: Boolean) {
        lifecycleScope.launch {
            viewModel.getLatestRates()
                if (networkEnabled) {
                    binding.tvNetworkStatus.text = getString(R.string.back_online)
                    binding.tvNetworkStatus.setBackgroundColor(Color.GREEN)
                    binding.tvNetworkStatus.visibility = View.VISIBLE
                    delay(3000)
                    binding.tvNetworkStatus.visibility = View.GONE
                } else {
                    binding.tvNetworkStatus.text = getString(R.string.offline)
                    binding.tvNetworkStatus.setBackgroundColor(Color.GRAY)
                    binding.tvNetworkStatus.visibility = View.VISIBLE
                    delay(3000)
                    binding.tvNetworkStatus.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkChangeReceiver)
    }

    private fun initUI() {
        setSupportActionBar(binding.toolbar)
        setupNavigation()
        lifecycleScope.launch {
            viewModel.errorMassager.collectLatest { error ->
                var errorMessage = ""
                errorMessage = when (error) {
                    is IOException -> {
                        getString(R.string.network_error)
                    }

                    else -> {
                        error?.message ?: ""
                    }
                }
                error?.let {
                    showErrorDialog(errorMessage)
                }
            }

        }





    }

    private fun showErrorDialog(error: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage(error)
            .setCancelable(false)
            .setNegativeButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }




    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}