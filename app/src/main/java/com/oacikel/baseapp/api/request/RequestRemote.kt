package com.oacikel.baseapp.api.request

import com.google.gson.GsonBuilder

data class RequestRemote constructor (
    var remoteRequest : String?
) {
    override fun toString(): String {
        return GsonBuilder().disableHtmlEscaping().create().toJson(this, RequestRemote::class.java)
    }
}