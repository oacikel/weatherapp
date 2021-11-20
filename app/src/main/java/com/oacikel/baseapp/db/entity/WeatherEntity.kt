package com.oacikel.baseapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import com.oacikel.baseapp.db.converters.*
import java.io.Serializable

@Entity(tableName = RoomConstants.WEATHER_TABLE)
data class WeatherEntity constructor(
    @PrimaryKey (autoGenerate = true)@ColumnInfo(name = RoomConstants.WEATHER_ID) @SerializedName(RoomConstants.WEATHER_ID) var id: Int =0,
    @TypeConverters(CoordinateConverter::class) @ColumnInfo(name = RoomConstants.WEATHER_COORDINATE) @SerializedName(RoomConstants.WEATHER_COORDINATE) var coordinate: CoordinateEntity? = null,
    @TypeConverters(SummaryListConverter::class) @ColumnInfo(name = RoomConstants.WEATHER_INFO) @SerializedName(RoomConstants.WEATHER_INFO) var weatherSummary: ArrayList<SummaryEntity> = arrayListOf(),
    @TypeConverters(DetailConverter::class) @ColumnInfo(name = RoomConstants.WEATHER_TEMP) @SerializedName(RoomConstants.WEATHER_TEMP) var detail: DetailEntity? = null,
    @ColumnInfo(name = RoomConstants.WEATHER_VISIBILITY) @SerializedName(RoomConstants.WEATHER_VISIBILITY) var visibility: Int? = 0,
    @TypeConverters(WindConverter::class) @ColumnInfo(name = RoomConstants.WEATHER_WIND) @SerializedName(RoomConstants.WEATHER_WIND) var wind: WindEntity? = null,
    @TypeConverters(CloudConverter::class) @ColumnInfo(name = RoomConstants.WEATHER_CLOUDS) @SerializedName(RoomConstants.WEATHER_CLOUDS) var clouds: CloudEntity? = null,
    @ColumnInfo(name = RoomConstants.WEATHER_CITY) @SerializedName(RoomConstants.WEATHER_CITY) var city: String? = "",
    @TypeConverters(WeatherLocationConverter::class) @ColumnInfo(name = RoomConstants.WEATHER_LOCATION) @SerializedName(RoomConstants.WEATHER_LOCATION) var locationDetail: LocationDetailEntity? = null,
    @ColumnInfo(name = RoomConstants.WEATHER_MESSAGE) @SerializedName(RoomConstants.WEATHER_MESSAGE) var message: String? = "",
    @ColumnInfo(name = RoomConstants.WEATHER_THROWABLE) @SerializedName(RoomConstants.WEATHER_THROWABLE)var throwable: String?="",
    @ColumnInfo(name = RoomConstants.WEATHER_CODE) @SerializedName(RoomConstants.WEATHER_CODE)var code: Int?=0,
    @ColumnInfo(name = RoomConstants.WEATHER_DATE) @SerializedName(RoomConstants.WEATHER_DATE)var date: String?=""
    ) : Serializable

