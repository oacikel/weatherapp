package com.oacikel.baseapp.db.converters.marvel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.marvelEntities.ComicEntity

object ComicEntityListConverter {
    @TypeConverter
    @JvmStatic
    fun fromComicEntityList(comics: List<ComicEntity>?): String? {
        if (comics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ComicEntity>>() {

        }.type
        return gson.toJson(comics, type)
    }

    @TypeConverter
    @JvmStatic
    fun toComicEntityList(comics: String?): List<ComicEntity>? {
        if (comics == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ComicEntity>>() {

        }.type
        return gson.fromJson<List<ComicEntity>>(comics, type)
    }
}