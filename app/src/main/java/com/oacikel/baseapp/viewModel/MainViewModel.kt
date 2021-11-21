package com.oacikel.baseapp.viewModel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oacikel.baseapp.db.dao.WeatherDao
import com.oacikel.baseapp.db.entity.WeatherEntity
import com.oacikel.baseapp.repository.WeatherRepository
import com.oacikel.baseapp.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject
constructor(val weatherRepository: WeatherRepository, val weatherDao: WeatherDao) :
    BaseViewModel() {
    val weatherLiveData: MutableLiveData<WeatherEntity>
    var savedWeatherList: MutableLiveData<List<WeatherEntity>>
    val LOG_TAG = "OCUL - MainviewModel"

    init {
        this.weatherLiveData = MutableLiveData()
        this.savedWeatherList = MutableLiveData()
    }

    fun getWeatherForLocation(location: Location) {
        weatherRepository.getWeatherForLocation(location, weatherLiveData)
    }

    fun getSavedWeather(viewLifecycleOwner: LifecycleOwner) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            weatherRepository.getLocalWeatherList().observe(viewLifecycleOwner) {
                savedWeatherList.postValue(it)
            }
        }
    }

    fun saveWeather(weatherEntity: WeatherEntity) {
        Log.d(LOG_TAG, "Id is" + weatherEntity.uniqueId)
        weatherRepository.addWeatherToLocal(weatherEntity)
    }
}