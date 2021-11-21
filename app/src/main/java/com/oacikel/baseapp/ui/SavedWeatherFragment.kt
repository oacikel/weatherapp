package com.oacikel.baseapp.ui

import android.os.Bundle
import android.util.Log
import com.oacikel.baseapp.MainActivity
import com.oacikel.baseapp.R
import com.oacikel.baseapp.api.Status
import com.oacikel.baseapp.binding.listAdapters.WeatherAdapter
import com.oacikel.baseapp.core.BaseInjectableFragment
import com.oacikel.baseapp.databinding.FragmentSavedWeatherBinding
import com.oacikel.baseapp.db.entity.WeatherEntity
import com.oacikel.baseapp.di.Injectable
import com.oacikel.baseapp.ui.callback.ListItemFocusCallback
import com.oacikel.baseapp.viewModel.SavedWeatherViewModel

class SavedWeatherFragment :
    BaseInjectableFragment<SavedWeatherViewModel, FragmentSavedWeatherBinding>(),
    Injectable, ListItemFocusCallback {

    override val layoutResourceId: Int = R.layout.fragment_saved_weather
    override val viewModelClass: Class<SavedWeatherViewModel> = SavedWeatherViewModel::class.java
    private val weatherAdapter by lazy { WeatherAdapter(this) }
    private val LOG_TAG="OCUL - SavedWeatherFragment"

    companion object {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        //binding sets
        Log.d(LOG_TAG,"Welcome to Saved weather fragment")
        binding.viewModel = viewModel
        binding.activity = (activity as MainActivity)
        binding.fragment = this@SavedWeatherFragment
        addAdapterToSavedWeatherList()
        fetchSavedWeatherList()
        viewModel.getSavedWeather()
    }

    fun addAdapterToSavedWeatherList() {
        binding.recyclerViewSavedWeathers.apply {
            adapter = weatherAdapter
        }
    }

    fun fetchSavedWeatherList() {
        viewModel.savedWeatherList.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it as MutableList<WeatherEntity>)
        }
        viewModel.getSavedWeather()
    }

    override fun onWeatherItemClicked(weather: WeatherEntity) {
        // TODO: handle erase events here
    }
}