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

class SearchCityViewModel @Inject
constructor(val weatherRepository: WeatherRepository, val weatherDao: WeatherDao) :
    BaseViewModel() {
    val weatherLiveData: MutableLiveData<WeatherEntity>
    var savedWeatherList: MutableLiveData<List<WeatherEntity>>

    init {
        this.weatherLiveData = MutableLiveData()
        this.savedWeatherList = MutableLiveData()
    }

    fun getWeatherForCity(city: String) {
        weatherRepository.getWeatherForCity(city, weatherLiveData)
    }

    fun saveWeather(weatherEntity: WeatherEntity) {
        weatherRepository.addWeatherToLocal(weatherEntity)
    }
}