package com.oacikel.baseapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.LocationDetailEntity

object WeatherLocationConverter {

    @TypeConverter
    @JvmStatic
    fun fromLocationDetail(location: LocationDetailEntity?): String? {
        if (location == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<LocationDetailEntity>() {

        }.type
        return gson.toJson(location, type)
    }

    @TypeConverter
    @JvmStatic
    fun toLocationDetail(location: String?): LocationDetailEntity? {
        if (location == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<LocationDetailEntity>() {

        }.type
        return gson.fromJson<LocationDetailEntity>(location, type)
    }
}