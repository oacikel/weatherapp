package com.oacikel.baseapp.db.entity

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable

data class SummaryEntity constructor(
    @ColumnInfo(name = RoomConstants.SUMMARY_ID) @SerializedName(RoomConstants.SUMMARY_ID) var id: Int? = 0,
    @ColumnInfo(name = RoomConstants.SUMMARY_MAIN) @SerializedName(RoomConstants.SUMMARY_MAIN) var main: String? = "",
    @ColumnInfo(name = RoomConstants.SUMMARY_DESC) @SerializedName(RoomConstants.SUMMARY_DESC) var description: String? = "",
    @ColumnInfo(name = RoomConstants.SUMMARY_ICON) @SerializedName(RoomConstants.SUMMARY_ICON) var icon: String? = "",
) : Serializable