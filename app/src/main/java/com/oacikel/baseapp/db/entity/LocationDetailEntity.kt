package com.oacikel.baseapp.db.entity

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable

data class LocationDetailEntity constructor(
    @ColumnInfo(name = RoomConstants.LOCATION_COUNTRY) @SerializedName(RoomConstants.LOCATION_COUNTRY) var countryCode: String? = "",
) : Serializable