package com.oacikel.baseapp.db.converters.marvel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.marvelEntities.ResourceListEntity

object ResourceListEntityConverter {
    @TypeConverter
    @JvmStatic
    fun fromResourceListEntity(resources: ResourceListEntity?): String? {
        if (resources == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ResourceListEntity>() {

        }.type
        return gson.toJson(resources, type)
    }

    @TypeConverter
    @JvmStatic
    fun toResourceListEntity(resources: String?): ResourceListEntity? {
        if (resources == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ResourceListEntity>() {

        }.type
        return gson.fromJson<ResourceListEntity>(resources, type)
    }
}