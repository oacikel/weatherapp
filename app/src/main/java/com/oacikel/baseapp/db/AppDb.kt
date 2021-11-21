package com.oacikel.baseapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oacikel.baseapp.db.converters.*
import com.oacikel.baseapp.db.dao.WeatherDao
import com.oacikel.baseapp.db.entity.WeatherEntity


/**
 * Main database description.
 */


@Database(
    entities = [
        WeatherEntity::class,
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(
    CloudConverter::class,
    CoordinateConverter::class,
    DetailConverter::class,
    SummaryListConverter::class,
    WindConverter::class,
    WeatherLocationConverter::class,
)
abstract class AppDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
