package com.oacikel.baseapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.CoordinateEntity

object CoordinateConverter {

    @TypeConverter
    @JvmStatic
    fun fromCoordinate(coordinate: CoordinateEntity?): String? {
        if (coordinate == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CoordinateEntity>() {

        }.type
        return gson.toJson(coordinate, type)
    }

    @TypeConverter
    @JvmStatic
    fun toCoordinate(coordinate: String?): CoordinateEntity? {
        if (coordinate == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CoordinateEntity>() {

        }.type
        return gson.fromJson<CoordinateEntity>(coordinate, type)
    }
}