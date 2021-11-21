package com.oacikel.baseapp.ui.callback

import com.oacikel.baseapp.db.entity.WeatherEntity

interface ListItemFocusCallback {
    fun onWeatherItemClicked(weather: WeatherEntity)
    fun onDeleteWeather(weather: WeatherEntity)
}