package com.oacikel.baseapp.api.request

data class RequestAccessToken(
    var refresh_token:String?,
    var DeviceID:String?
)