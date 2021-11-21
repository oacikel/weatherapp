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

class SavedWeatherViewModel @Inject
constructor(val weatherRepository: WeatherRepository, val weatherDao: WeatherDao) :
    BaseViewModel() {
    var savedWeatherList:LiveData<List<WeatherEntity>>

    init {
        this.savedWeatherList=MutableLiveData()
    }
    fun getSavedWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            savedWeatherList=weatherRepository.getLocalWeatherList()
        }
    }
}