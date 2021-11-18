package com.oacikel.baseapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LocationReceiver(var locationReceiverListener : LocationReceiverListener? = null) : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        if (locationReceiverListener != null){
            locationReceiverListener?.onLocationEnableChanged(context!!.locationEnable())
        }
    }

    interface LocationReceiverListener{
        fun onLocationEnableChanged(isEnabled : Boolean)
    }
}