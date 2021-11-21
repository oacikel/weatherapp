package com.oacikel.baseapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.oacikel.baseapp.db.entity.WeatherEntity

@Dao
interface WeatherDao {

    @get:Query("SELECT * FROM weather")
    val savedWeatherList: LiveData<List<WeatherEntity>>

    @Query("SELECT * FROM weather WHERE  (name LIKE '%' || :searchedText || '%' OR sys LIKE '%' || :searchedText || '%')")
    fun searchWeatherList(searchedText: String): LiveData<List<WeatherEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeatherList(weatherEntity: List<WeatherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherEntity: WeatherEntity)

    @Delete
    fun deleteWeatherEntity(weatherEntity: WeatherEntity)


    @Query("DELETE FROM weather")
    fun deleteAll()
}