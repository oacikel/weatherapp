package com.oacikel.baseapp.db.entity

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable

data class WindEntity constructor(
    @ColumnInfo(name = RoomConstants.WIND_SPEED) @SerializedName(RoomConstants.WIND_SPEED) var speed: Double? = 0.0,
    @ColumnInfo(name = RoomConstants.WIND_DEGREE) @SerializedName(RoomConstants.WIND_DEGREE) var degree: Int? =0,
) : Serializable