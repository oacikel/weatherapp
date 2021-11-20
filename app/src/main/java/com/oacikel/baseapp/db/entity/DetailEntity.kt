package com.oacikel.baseapp.db.entity

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable

data class DetailEntity constructor(
    @ColumnInfo(name = RoomConstants.DETAIL_TEMP) @SerializedName(RoomConstants.DETAIL_TEMP) var temperature: Double? = 0.0,
    @ColumnInfo(name = RoomConstants.DETAIL_FEELS_LIKE) @SerializedName(RoomConstants.DETAIL_FEELS_LIKE) var feelsLike: Double? = 0.0,
    @ColumnInfo(name = RoomConstants.DETAIL_TEMP_MIN) @SerializedName(RoomConstants.DETAIL_TEMP_MIN) var minTemperature: Double? = 0.0,
    @ColumnInfo(name = RoomConstants.DETAIL_MAX) @SerializedName(RoomConstants.DETAIL_MAX) var maxTemperature: Double? = 0.0,
    @ColumnInfo(name = RoomConstants.DETAIL_PRESSURE) @SerializedName(RoomConstants.DETAIL_PRESSURE) var pressure: Int? = 0,
    @ColumnInfo(name = RoomConstants.DETAIL_HUMIDITY) @SerializedName(RoomConstants.DETAIL_HUMIDITY) var humidity: Int? = 0,
) : Serializable