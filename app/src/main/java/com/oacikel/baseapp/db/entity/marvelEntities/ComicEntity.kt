package com.oacikel.baseapp.db.entity.marvelEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import com.oacikel.baseapp.db.converters.marvel.ImageEntityConverter
import com.oacikel.baseapp.db.converters.marvel.URLEntityListConverter
import java.io.Serializable

@Entity(tableName = RoomConstants.COMIC_TABLE)
data class ComicEntity constructor(
    @PrimaryKey @ColumnInfo(name = RoomConstants.COMIC_ID) @SerializedName(RoomConstants.COMIC_ID) var id: Int =0,
    @ColumnInfo(name = RoomConstants.COMIC_TITLE) @SerializedName(RoomConstants.COMIC_TITLE) var title: String? = "",
    @ColumnInfo(name = RoomConstants.COMIC_DESCRIPTION) @SerializedName(RoomConstants.COMIC_DESCRIPTION) var description: String? = "",
    @TypeConverters(ImageEntityConverter::class) @ColumnInfo(name = RoomConstants.COMIC_THUMBNAIL) @SerializedName(RoomConstants.COMIC_THUMBNAIL) var thumbnail: ImageEntity? = null,
) : Serializable