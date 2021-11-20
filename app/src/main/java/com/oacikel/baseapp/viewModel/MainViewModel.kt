package com.oacikel.baseapp.viewModel

import androidx.lifecycle.LiveData
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
    val weatherLiveData: SingleLiveEvent<WeatherEntity>
    var savedWeatherList:LiveData<List<WeatherEntity>>

    init {
        this.weatherLiveData = SingleLiveEvent()
        this.savedWeatherList=MutableLiveData()
    }

    fun getWeatherForCity() {
        weatherRepository.getWeatherForCity("london", weatherLiveData)
        weatherRepository.getWeatherForLocation(location.value,weatherLiveData)
    }
    fun getWeatherForLocation(){
        weatherRepository.getWeatherForLocation(location.value,weatherLiveData)
    }
    fun getSavedWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            savedWeatherList=weatherRepository.getLocalWeatherList()
        }
    }

    fun saveWeather(weatherEntity: WeatherEntity){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepository.addWeatherToLocal(weatherEntity)
        }
    }
}