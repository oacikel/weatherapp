package com.oacikel.baseapp.api.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseExceptionListModel(@SerializedName("typeCode") var typeCode : String? = null,
                                      @SerializedName("message") var message : String? = null,
                                      @SerializedName("expectedValue") var expectedValue  :String? = null,
                                      @SerializedName("exceptionDetail") var exceptionDetail  :String? = null,
                                      @SerializedName("code") var code  :String? = null):
    Serializable