package com.oacikel.baseapp.ui

import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.oacikel.baseapp.MainActivity
import com.oacikel.baseapp.R
import com.oacikel.baseapp.binding.listAdapters.WeatherAdapter
import com.oacikel.baseapp.core.BaseInjectableFragment
import com.oacikel.baseapp.databinding.FragmentSavedWeatherBinding
import com.oacikel.baseapp.db.entity.WeatherEntity
import com.oacikel.baseapp.db.enums.TemperatureUnits
import com.oacikel.baseapp.di.Injectable
import com.oacikel.baseapp.ui.callback.ListItemFocusCallback
import com.oacikel.baseapp.viewModel.SavedWeatherViewModel

class SavedWeatherFragment :
    BaseInjectableFragment<SavedWeatherViewModel, FragmentSavedWeatherBinding>(),
    Injectable, ListItemFocusCallback {

    override val layoutResourceId: Int = R.layout.fragment_saved_weather
    override val viewModelClass: Class<SavedWeatherViewModel> = SavedWeatherViewModel::class.java
    private val weatherAdapter by lazy { WeatherAdapter(TemperatureUnits.CELSIUS, this) }
    private val LOG_TAG = "OCUL - SavedWeatherFragment"

    companion object {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        //binding sets
        Log.d(LOG_TAG, "Welcome to Saved weather fragment")
        binding.viewModel = viewModel
        binding.activity = (activity as MainActivity)
        binding.fragment = this@SavedWeatherFragment
        addAdapterToSavedWeatherList()
        fetchSavedWeatherList()
        binding.textInputLayoutSearchSavedWeathers.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                viewModel.searchFor(viewLifecycleOwner, it.toString())
            }
        }
    }

    fun addAdapterToSavedWeatherList() {
        binding.recyclerViewSavedWeathers.apply {
            adapter = weatherAdapter
        }
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(binding.recyclerViewSavedWeathers)
    }

    fun fetchSavedWeatherList() {
        viewModel.savedWeatherList.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it as MutableList<WeatherEntity>)
            binding.adapter = weatherAdapter
        }
        viewModel.getSavedWeather(viewLifecycleOwner)
    }

    override fun onWeatherItemClicked(weather: WeatherEntity) {
        // TODO: handle erase events here
    }

    override fun onDeleteWeather(weather: WeatherEntity) {
        viewModel.deleteWeather(weather)
    }
}