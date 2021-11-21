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
                    GlobalScope.launch(Dispatchers.IO) {
                        weatherDao.insertWeather(response.body()!!)
                    }

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
                    if(response.body()!=null){
                        GlobalScope.launch(Dispatchers.IO) {
                            weatherDao.insertWeather(response.body()!!)
                        }
                    }

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
        weatherDao.insertWeather(weatherEntity)
    }


    /*
    fun loadHomePage(
        date: String?,
        location: String?,
        isConnect: Boolean
    ): LiveData<Resource<HomeEntity>> {
        return object : LoyaltyNetworkBoundResource<HomeEntity, ResponseResultModel<HomeEntity>>(
            appExecutors,
            isConnect
        ) {
            override fun saveCallResult(item: ResponseResultModel<HomeEntity>) {
                //save result
                homeDao.saveHomePage(item.result)
            }

            override fun shouldFetch(data: HomeEntity?): Boolean {
                //fetch it
                return isConnect&&(location!=null)
            }

            override fun loadFromDb(): LiveData<HomeEntity> {
                //createUser(userPostsRequestModel.userId.toString())
                return homeDao.homePage
            }

            override fun createCall() = loyaltyService.requestHomepage(date,location)
        }.asLiveData()
    }

     */

}
