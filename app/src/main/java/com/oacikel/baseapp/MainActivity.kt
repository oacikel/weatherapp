package com.oacikel.baseapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import com.oacikel.baseapp.core.BaseInjectableActivity
import com.oacikel.baseapp.databinding.ActivityMainBinding
import com.oacikel.baseapp.viewModel.MainViewModel
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : BaseInjectableActivity<MainViewModel, ActivityMainBinding>(),
    EasyPermissions.PermissionCallbacks {
    override val layoutResourceId: Int = R.layout.activity_main
    override val viewModelClass: Class<MainViewModel> = MainViewModel::class.java
    lateinit var appSettings: AppSettings
    var destination: Int = R.id.mainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Do not have permissions, request them now
        EasyPermissions.requestPermissions(
            this@MainActivity,
            getString(R.string.permission_location),
            101,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        getAuthenticatedUser()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        getAuthenticatedUser()
        loadNavigation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(101)
    fun getAuthenticatedUser() {

    }

    fun getAppSettingsDatastore(): AppSettings {
        runBlocking {
            appSettings = appSettingsDataStore.data.first()
        }
        return appSettings
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        super.onBackPressed()
        when ((Navigation.findNavController(
            this,
            R.id.container
        ).currentDestination as FragmentNavigator.Destination).label) {
        }
    }

    private fun loadNavigation() {
        val graphInflater = Navigation.findNavController(this, R.id.container).navInflater
        val navGraph = graphInflater.inflate(R.navigation.main)
        Navigation.findNavController(this, R.id.container).setGraph(R.navigation.main)
        binding.bottomNavigationView.menu.clear()
        binding.bottomNavigationView.inflateMenu(R.menu.navigation_items)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mainFragment -> {
                    Navigation.findNavController(this, R.id.container)
                        .popBackStack(navGraph.startDestination, true)
                    Navigation.findNavController(this, R.id.container)
                        .navigate(R.id.mainFragment)
                }
                R.id.savedWeather -> {
                    Navigation.findNavController(this, R.id.container)
                        .popBackStack(navGraph.startDestination, true)
                    Navigation.findNavController(this, R.id.container)
                        .navigate(R.id.savedWeatherFragment)
                }
            }
            true
        }
    }

}


