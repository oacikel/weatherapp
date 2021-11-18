package com.oacikel.baseapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.oacikel.baseapp.db.RoomConstants
import java.io.Serializable

@Entity(tableName = RoomConstants.USER_TABLE)
data class UserEntity constructor(@PrimaryKey @ColumnInfo(name = RoomConstants.USER_ID)@SerializedName(RoomConstants.USER_ID) var id : String = "",
                                  @ColumnInfo(name = RoomConstants.USER_GUID)@SerializedName(RoomConstants.USER_GUID) var userGuid : String? = "",
                                  @ColumnInfo(name = RoomConstants.USER_NAME)@SerializedName(RoomConstants.USER_NAME) var userName : String? = "",
                                  @ColumnInfo(name = RoomConstants.USER_FIRST_NAME)@SerializedName(RoomConstants.USER_FIRST_NAME,alternate= ["name"]) var userFirstName : String? = "",
                                  @ColumnInfo(name = RoomConstants.USER_LAST_NAME)@SerializedName(RoomConstants.USER_LAST_NAME,alternate= ["surname"]) var userLastName : String? = "",
                                  @ColumnInfo(name = RoomConstants.USER_MAIL)@SerializedName(RoomConstants.USER_MAIL) var userMail : String? = "",
                                  @ColumnInfo(name = RoomConstants.USER_ROLE) var userRole : String? = "",
                                  @ColumnInfo(name = RoomConstants.USER_ROLE_ID) var userRoleId : String? = "",
                                  @ColumnInfo(name = RoomConstants.USER_MARKET_CODE)@SerializedName(RoomConstants.USER_MARKET_CODE) var userMarketCode : Int = 1,
                                  @ColumnInfo(name = RoomConstants.USER_IMAGE_URL)@SerializedName("imageUrl",alternate = ["userImageUrl"]) var userImageUrl : String? = "",
                                                                    ): Serializable

