package com.oacikel.baseapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.WindEntity

object WindConverter {

    @TypeConverter
    @JvmStatic
    fun fromWind(wind: WindEntity?): String? {
        if (wind == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<WindEntity>() {

        }.type
        return gson.toJson(wind, type)
    }

    @TypeConverter
    @JvmStatic
    fun toWind(wind: String?): WindEntity? {
        if (wind == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<WindEntity>() {

        }.type
        return gson.fromJson<WindEntity>(wind, type)
    }
}