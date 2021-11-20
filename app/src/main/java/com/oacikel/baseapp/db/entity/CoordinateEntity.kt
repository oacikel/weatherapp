package com.oacikel.baseapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable

data class CoordinateEntity constructor(
    @ColumnInfo(name = RoomConstants.COORDINATE_LAT) @SerializedName(RoomConstants.COORDINATE_LAT) var latitude: Double? = null,
    @ColumnInfo(name = RoomConstants.COORDINATE_LNG) @SerializedName(RoomConstants.COORDINATE_LNG) var longitude: Double? = null,
) : Serializable