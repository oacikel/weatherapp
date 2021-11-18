package com.oacikel.baseapp.db.converters.marvel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.marvelEntities.SummaryViewEntity

object SummaryViewEntityListConverter {
    @TypeConverter
    @JvmStatic
    fun fromSummaryViewEntityList(summaries: List<SummaryViewEntity>?): String? {
        if (summaries == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<SummaryViewEntity>>() {

        }.type
        return gson.toJson(summaries, type)
    }

    @TypeConverter
    @JvmStatic
    fun toSummaryViewEntityList(summaries: String?): List<SummaryViewEntity>? {
        if (summaries == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<SummaryViewEntity>>() {

        }.type
        return gson.fromJson<List<SummaryViewEntity>>(summaries, type)
    }
}