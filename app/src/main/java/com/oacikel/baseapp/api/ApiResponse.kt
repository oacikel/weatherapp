package com.oacikel.baseapp.api

import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.api.response.ResponseDataModel
import com.oacikel.baseapp.api.response.ResponseExceptionListModel
import com.oacikel.baseapp.api.response.ResponseMeta
import com.oacikel.baseapp.db.RoomConstants
import com.oacikel.baseapp.util.tryCatch
import timber.log.Timber
import retrofit2.Response

/**
"code": 200,
"status": "Ok",
"copyright": "© 2021 MARVEL",
"attributionText": "Data provided by Marvel. © 2021 MARVEL",
"attributionHTML": "<a href=\"http://marvel.com\">Data provided by Marvel. © 2021 MARVEL</a>",
"etag": "38aa13ab62b9572b5dc9986737c54f2ef51e7ebe",
"data": {
"offset": 0,
"limit": 20,
"total": 1559,
"count": 20,
"results": [
 */
data class ApiResponse<T>(@SerializedName(RoomConstants.MARVEL_RESPONSE_CODE) var code : Int = 0,
                          @SerializedName(RoomConstants.MARVEL_RESPONSE_STATUS) var status : String = "",
                          @SerializedName(RoomConstants.MARVEL_RESPONSE_COPYRIGHT) var copyRight : String = "",
                          @SerializedName(RoomConstants.MARVEL_RESPONSE_ATTR_TEXT) var attributionText : String = "",
                          @SerializedName(RoomConstants.MARVEL_RESPONSE_ATTR_HTML) var attributionHTML : String = "",
                          @SerializedName(RoomConstants.MARVEL_RESPONSE_ETAG) var etag : String = "",
                          @SerializedName(RoomConstants.MARVEL_RESPONSE_DATA) var data: ResponseDataModel<T>? = null,
                          @SerializedName("exceptionList") var exceptionList : ArrayList<ResponseExceptionListModel>? = null,
                          @SerializedName("isFaulted",alternate = ["isFault"]) var isFaulted : Boolean = false) {

    var httpCode: Int? = 0

    @JvmOverloads
    constructor(error: Throwable) : this() {
        httpCode = 500
        data = null
    }
    @JvmOverloads
    constructor(response : Response<T>) : this() {
        httpCode = response.code()
        if (response.isSuccessful) {
            data = ResponseDataModel(response.body()!!)
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                tryCatch(tryBlock = {
                    message = response.errorBody()!!.string()
                } , catchBlock = { Timber.e("$it")})
            }

            if (message == null || message?.trim { it <= ' ' }!!.isEmpty()) {
                message = response.message()
            }

            data = null
        }
    }
    @JvmOverloads
    constructor(body : T) : this() {
        data = ResponseDataModel(body)
    }

    val isSuccessful: Boolean
        get() = code in 200..299

}
