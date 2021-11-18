package com.oacikel.baseapp.api.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseLoginModel(
    var access_token : String? ,
    var token_type : String? ,
    var expires_in : String? ,
    var refresh_token : String?) : Parcelable