package com.oacikel.baseapp.db.entity.marvelEntities

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import com.oacikel.baseapp.db.converters.marvel.SummaryViewEntityListConverter
import java.io.Serializable

data class ResourceListEntity constructor(
    @ColumnInfo(name = RoomConstants.RESOURCE_AVAILABLE) @SerializedName(RoomConstants.RESOURCE_AVAILABLE) var availableCount: Int = 0,
    @ColumnInfo(name = RoomConstants.RESOURCE_RETURNED) @SerializedName(RoomConstants.RESOURCE_RETURNED) var availableReturned: Int? = 0,
    @ColumnInfo(name = RoomConstants.RESOURCE_COLLECTION_URI) @SerializedName(RoomConstants.RESOURCE_COLLECTION_URI) var collectionURI: String? = "",
    @TypeConverters(SummaryViewEntityListConverter::class) @ColumnInfo(name = RoomConstants.RESOURCE_ITEMS) @SerializedName(RoomConstants.RESOURCE_ITEMS) var collectionItems: List<SummaryViewEntity>? = listOf()
) : Serializable