package com.oacikel.baseapp.api.response

import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants

class ResponseDataModel<T>(@SerializedName(RoomConstants.MARVEL_RESPONSE_RESULTS) var content : T)