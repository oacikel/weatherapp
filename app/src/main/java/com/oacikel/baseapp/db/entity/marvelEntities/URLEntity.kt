package com.oacikel.baseapp.db.entity.marvelEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable

// TODO: Convert the type parameter to Enum
data class URLEntity constructor(@ColumnInfo(name = RoomConstants.URL_TYPE)@SerializedName(RoomConstants.URL_TYPE) var type : String? = "",
                                @ColumnInfo(name = RoomConstants.URL_URL)@SerializedName(RoomConstants.URL_URL) var url : String? = "", ): Serializable

