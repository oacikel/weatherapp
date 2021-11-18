package com.oacikel.baseapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ConnectivityReceiver(var connectivityChangedListener : ConnectivityReceiverListener? = null) : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        if (connectivityChangedListener != null){
            connectivityChangedListener?.onNetworkConnectChanged(context!!.connect())
        }
    }

    interface ConnectivityReceiverListener{
        fun onNetworkConnectChanged(isConnected : Boolean)
    }
}