package com.oacikel.baseapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oacikel.baseapp.db.entity.SummaryEntity

object SummaryListConverter {
    @TypeConverter
    @JvmStatic
    fun fromSummayEntityList(summaries: ArrayList<SummaryEntity>?): String? {
        if (summaries == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<SummaryEntity>>() {

        }.type
        return gson.toJson(summaries, type)
    }

    @TypeConverter
    @JvmStatic
    fun toSummaryList(summaries: String?): ArrayList<SummaryEntity>? {
        if (summaries == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<SummaryEntity>>() {

        }.type
        return gson.fromJson<ArrayList<SummaryEntity>>(summaries, type)
    }
}