package com.oacikel.baseapp.db.entity.marvelEntities

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable

data class SummaryViewEntity constructor(
    @ColumnInfo(name = RoomConstants.SUMMARY_NAME) @SerializedName(RoomConstants.SUMMARY_NAME) var summary: String = "",
    @ColumnInfo(name = RoomConstants.SUMMARY_URI) @SerializedName(RoomConstants.SUMMARY_URI) var uri: String = "",
    ):Serializable