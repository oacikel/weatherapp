package com.oacikel.baseapp.util

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData

fun Context.connect(): Boolean {
    val connMgr = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connMgr.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnectedOrConnecting
}

fun Context.locationEnable(): Boolean {
    val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun Any.tryCatch(
    tryBlock: () -> Unit,
    catchBlock: ((t: Throwable) -> Unit)? = null,
    finalBlock: (() -> Unit)? = null
) {
    try {
        tryBlock()
    } catch (ex: Exception) {
        catchBlock?.invoke(ex)
    } finally {
        finalBlock?.invoke()
    }
}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
