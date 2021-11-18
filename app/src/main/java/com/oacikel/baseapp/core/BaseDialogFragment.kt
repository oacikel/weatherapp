package com.oacikel.baseapp.core

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.oacikel.baseapp.util.Constants
import java.io.Serializable

abstract class BaseDialogFragment : DialogFragment() {

    protected lateinit var progress: ProgressDialog

    companion object {

        fun <T> newInstance(fragment: T, o: Any? = null): T {
            var bundle = Bundle()

            if (o != null) {
                when (o) {
                    is String -> bundle.putString(Constants.FRAGMENT_DATA, o)
                    is Boolean -> bundle.putBoolean(Constants.FRAGMENT_DATA, o)
                    is Int -> bundle.putInt(Constants.FRAGMENT_DATA, o)
                    is Float -> bundle.putFloat(Constants.FRAGMENT_DATA, o)
                    is Byte -> bundle.putByte(Constants.FRAGMENT_DATA, o)
                    is ByteArray -> bundle.putByteArray(Constants.FRAGMENT_DATA, o)
                    is Char -> bundle.putChar(Constants.FRAGMENT_DATA, o)
                    is Short -> bundle.putShort(Constants.FRAGMENT_DATA, o)
                    is IntArray -> bundle.putIntArray(Constants.FRAGMENT_DATA, o)
                    is Parcelable -> bundle.putParcelable(Constants.FRAGMENT_DATA, o)
                    is Serializable -> bundle.putSerializable(Constants.FRAGMENT_DATA, o)
                }
            }

            (fragment as Fragment).arguments = bundle
            return fragment
        }
    }
}