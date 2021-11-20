package com.oacikel.baseapp.db.entity

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable

data class CloudEntity constructor(
    @ColumnInfo(name = RoomConstants.CLOUD_ALL) @SerializedName(RoomConstants.CLOUD_ALL) var all: Double? = 0.0,
) : Serializable