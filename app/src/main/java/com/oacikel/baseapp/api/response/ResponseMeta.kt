package com.oacikel.baseapp.api.response

import com.google.gson.annotations.SerializedName

data class ResponseMeta(@SerializedName("version") var version :String?,
                        @SerializedName("assemblyVersion") var assemblyVersion : String?)