package com.oacikel.baseapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oacikel.baseapp.db.entity.UserEntity

@Dao
interface UserDao {

    //dont change
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createUser(userEntity: UserEntity)
    //dont change

    @get:Query("SELECT * FROM USER_TABLE")
    val user: LiveData<UserEntity>


    @Query("UPDATE USER_TABLE SET id=:userGuid,userName=:userName,firstName=:userFirstName,lastName=:userLastName,mail=:userMail,marketCode=:userMarketCode WHERE id=:userGuid")
    fun updateUserDataWithToken(
        userGuid: String,
        userName: String?,
        userFirstName: String?,
        userLastName: String?,
        userMail: String?,
        userMarketCode: Int?
    )

    @Query("SELECT * FROM USER_TABLE WHERE guid =:id")
    fun findByGuid(id: String): UserEntity
}
