package com.oacikel.baseapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oacikel.baseapp.db.converters.marvel.*
import com.oacikel.baseapp.db.dao.UserDao
import com.oacikel.baseapp.db.entity.UserEntity
import com.oacikel.baseapp.db.entity.marvelEntities.CharacterEntity
import com.oacikel.baseapp.db.entity.marvelEntities.ComicEntity


/**
 * Main database description.
 */


@Database(
    entities = [
        UserEntity::class,
        CharacterEntity::class,
        ComicEntity::class,
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    ImageEntityConverter::class,
    URLEntityListConverter::class,
    ComicEntityListConverter::class,
    SummaryViewEntityListConverter::class,
    ResourceListEntityConverter::class
)
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): UserDao
}
