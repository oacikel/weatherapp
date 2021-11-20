package com.oacikel.baseapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.DetailEntity

object DetailConverter {

    @TypeConverter
    @JvmStatic
    fun fromDetail(detail: DetailEntity?): String? {
        if (detail == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<DetailEntity>() {

        }.type
        return gson.toJson(detail, type)
    }

    @TypeConverter
    @JvmStatic
    fun toDetail(detail: String?): DetailEntity? {
        if (detail == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<DetailEntity>() {

        }.type
        return gson.fromJson<DetailEntity>(detail, type)
    }
}