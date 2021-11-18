package com.oacikel.baseapp.db.converters.marvel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.marvelEntities.ImageEntity

object ImageEntityConverter {

    @TypeConverter
    @JvmStatic
    fun fromImageEntityList(images: ImageEntity?): String? {
        if (images == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ImageEntity>() {

        }.type
        return gson.toJson(images, type)
    }

    @TypeConverter
    @JvmStatic
    fun toImageEntityList(images: String?): ImageEntity? {
        if (images == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ImageEntity>() {

        }.type
        return gson.fromJson<ImageEntity>(images, type)
    }
}