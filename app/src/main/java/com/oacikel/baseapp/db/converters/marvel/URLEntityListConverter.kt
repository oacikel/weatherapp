package com.oacikel.baseapp.db.converters.marvel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.marvelEntities.URLEntity

object URLEntityListConverter {
    @TypeConverter
    @JvmStatic
    fun fromURLEntityList(urls: List<URLEntity>?): String? {
        if (urls == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<URLEntity>>() {

        }.type
        return gson.toJson(urls, type)
    }

    @TypeConverter
    @JvmStatic
    fun toURLEntityList(urls: String?): List<URLEntity>? {
        if (urls == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<URLEntity>>() {

        }.type
        return gson.fromJson<List<URLEntity>>(urls, type)
    }
}