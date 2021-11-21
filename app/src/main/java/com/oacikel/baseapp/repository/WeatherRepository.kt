package com.oacikel.baseapp.repository

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oacikel.baseapp.api.BaseService
import com.oacikel.baseapp.db.dao.WeatherDao
import com.oacikel.baseapp.db.entity.WeatherEntity
import com.oacikel.baseapp.db.enums.TemperatureUnits
import com.oacikel.baseapp.util.getCurrentDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val baseService: BaseService,
    private val weatherDao: WeatherDao
) {
    fun getWeatherForCity(
        cityName: String,
        weatherLiveData: MutableLiveData<WeatherEntity>
    ) {
        baseService.getWeatherForCity(cityName, TemperatureUnits.CELSIUS.unit)
            .enqueue(object : Callback<WeatherEntity> {
                override fun onResponse(
                    call: Call<WeatherEntity>,
                    response: Response<WeatherEntity>
                ) {
                    response.body()?.date = getCurrentDate()
                    weatherLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                    weatherLiveData.postValue(
                        WeatherEntity(
                            throwable = t.localizedMessage,
                            message = t.message
                        )
                    )
                }
            })
    }

    fun getWeatherForLocation(
        location: Location?,
        weatherLiveData: MutableLiveData<WeatherEntity>
    ) {
        baseService.getWeatherForLocation(
            latitude = location?.latitude.toString(),
            longitude = location?.longitude.toString(),
            TemperatureUnits.CELSIUS.unit
        )
            .enqueue(object : Callback<WeatherEntity> {
                override fun onResponse(
                    call: Call<WeatherEntity>,
                    response: Response<WeatherEntity>
                ) {
                    response.body()?.date = getCurrentDate()
                    weatherLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                    weatherLiveData.postValue(
                        WeatherEntity(
                            throwable = t.localizedMessage,
                            message = t.message
                        )
                    )
                }
            })
    }

    fun getLocalWeatherList(): LiveData<List<WeatherEntity>> {
        return weatherDao.savedWeatherList
    }

    fun addWeatherToLocal(weatherEntity: WeatherEntity) {
        Log.d("OCUL", "Dao size: " + weatherDao.savedWeatherList.value?.size)
        GlobalScope.launch(Dispatchers.IO) { weatherDao.insertWeather(weatherEntity) }
    }

    fun removeWeatherFromLocal(weatherEntity: WeatherEntity) {
        GlobalScope.launch(Dispatchers.IO) { weatherDao.deleteWeatherEntity(weatherEntity) }
    }

    fun searchFor(string: String): LiveData<List<WeatherEntity>> {
        return weatherDao.searchWeatherList(string)
    }

}
