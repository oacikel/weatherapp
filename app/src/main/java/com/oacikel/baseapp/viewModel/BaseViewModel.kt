package com.oacikel.baseapp.viewModel

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import com.oacikel.baseapp.BaseApplication
import com.oacikel.baseapp.R
import com.oacikel.baseapp.api.Status
import com.oacikel.baseapp.core.BaseActivity
import com.oacikel.baseapp.db.AppDb
import com.oacikel.baseapp.db.entity.WeatherEntity
import com.oacikel.baseapp.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel : ViewModel(), ConnectivityReceiver.ConnectivityReceiverListener,
    LocationReceiver.LocationReceiverListener {

    internal open var status = SingleLiveEvent<Status>()
    internal open var errorMessage = SingleLiveEvent<String>().default(
        (BaseApplication.activity as BaseActivity).resources.getString(
            R.string.common_unknown_error
        )
    )
    internal open var loadingMessage = MutableLiveData<String>().default(
        (BaseApplication.activity as BaseActivity).resources.getString(
            R.string.loading_popup_default_message
        )
    )

    internal open var successMessage = MutableLiveData<String>().default(
        (BaseApplication.activity as BaseActivity).resources.getString(
            R.string.success_popup_default_message
        )
    )
    internal var isConnect = MutableLiveData<Boolean>().default(true)
    internal var isLocationEnable = MutableLiveData<Boolean>().default(false)
    private var networkReceiver: ConnectivityReceiver = ConnectivityReceiver(this)
    private var locationReceiver: LocationReceiver = LocationReceiver(this)

    var location: MutableLiveData<Location> = MutableLiveData()

    @Inject
    lateinit var appDatabase: AppDb

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null

    init {
        BaseApplication.get()!!.registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        BaseApplication.get()!!.registerReceiver(
            locationReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )

        locationRequest = LocationRequest.create().apply {
            this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            this.interval = 10 * 1000
            this.fastestInterval = 5 * 1000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (loc in locationResult.locations){
                    // Update UI with location data
                    location.postValue(loc)
                }
            }
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(BaseApplication.activity)
        isLocationEnable.value = BaseApplication.get()!!.locationEnable()
        getLocation()
    }

    fun setLoading(mStatus: Status) {
        this.status = SingleLiveEvent()
        this.status.value = mStatus
    }

    override fun onNetworkConnectChanged(isConnected: Boolean) {
        if (Objects.equals(isConnect.value, isConnected)) {
            return
        }
        isConnect.value = isConnected
    }

    override fun onLocationEnableChanged(isEnabled: Boolean) {
        if (Objects.equals(isLocationEnable.value, isEnabled)) {
            return
        }
        isLocationEnable.value = isEnabled
    }

    fun clearLocalDB() {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.clearAllTables()
        }
    }


    fun getLocation(): LiveData<Location> {
        if (ActivityCompat.checkSelfPermission(
                BaseApplication.activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                BaseApplication.activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            location.value = null
            return location
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { loc: Location? ->
                if(loc != null){
                    location.postValue(loc)
                }else{
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest!!, locationCallback!!, null);
                }
            }
        return location
    }

    fun stopLocationRequest(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback!!);
    }
}
