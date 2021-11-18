package com.oacikel.baseapp.db.entity.marvelEntities

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable


data class ImageEntity constructor(
    @ColumnInfo(name = RoomConstants.IMAGE_PATH)@SerializedName(RoomConstants.IMAGE_PATH) var path : String? = "",
    @ColumnInfo(name = RoomConstants.IMAGE_EXTENSION)@SerializedName(RoomConstants.IMAGE_EXTENSION) var extension : String? = "",
): Serializable

