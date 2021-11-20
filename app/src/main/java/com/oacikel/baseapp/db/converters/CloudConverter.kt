package com.oacikel.baseapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.CloudEntity

object CloudConverter {
    @TypeConverter
    @JvmStatic
    fun fromCloud(cloud: CloudEntity?): String? {
        if (cloud == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CloudEntity>() {

        }.type
        return gson.toJson(cloud, type)
    }

    @TypeConverter
    @JvmStatic
    fun toCloud(cloud: String?): CloudEntity? {
        if (cloud == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CloudEntity>() {

        }.type
        return gson.fromJson<CloudEntity>(cloud, type)
    }
}