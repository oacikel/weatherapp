package com.oacikel.baseapp.viewModel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
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

class SavedWeatherViewModel @Inject
constructor(val weatherRepository: WeatherRepository, val weatherDao: WeatherDao) :
    BaseViewModel() {
    var savedWeatherList: MutableLiveData<List<WeatherEntity>>


    init {
        this.savedWeatherList=MutableLiveData()
    }
    fun getSavedWeather(viewLifecycleOwner: LifecycleOwner) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            weatherRepository.getLocalWeatherList().observe(viewLifecycleOwner) {
                savedWeatherList.postValue(it)
            }
        }
    }
    fun deleteWeather(weatherEntity: WeatherEntity) {
        weatherRepository.removeWeatherFromLocal(weatherEntity)
    }

    fun searchFor(viewLifecycleOwner: LifecycleOwner,string: String) {
        weatherRepository.searchFor(string).observe(viewLifecycleOwner){
            if(it.isNotEmpty())
            savedWeatherList.postValue(it)
        }
    }
}