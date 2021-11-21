package com.oacikel.baseapp.ui

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.oacikel.baseapp.MainActivity
import com.oacikel.baseapp.R
import com.oacikel.baseapp.api.Status
import com.oacikel.baseapp.binding.listAdapters.WeatherAdapter
import com.oacikel.baseapp.core.BaseInjectableFragment
import com.oacikel.baseapp.databinding.FragmentMainBinding
import com.oacikel.baseapp.db.entity.WeatherEntity
import com.oacikel.baseapp.db.enums.TemperatureUnits
import com.oacikel.baseapp.di.Injectable
import com.oacikel.baseapp.ui.callback.ListItemFocusCallback
import com.oacikel.baseapp.viewModel.MainViewModel


class MainFragment : BaseInjectableFragment<MainViewModel, FragmentMainBinding>(),
    Injectable, ListItemFocusCallback {

    override val layoutResourceId: Int = R.layout.fragment_main
    override val viewModelClass: Class<MainViewModel> = MainViewModel::class.java
    private lateinit var weatherEntity: WeatherEntity
    private val weatherAdapter by lazy { WeatherAdapter(this) }
    private val LOG_TAG = "OCUL - MainFragment"
    private var isFirstWeatherRetrieved = false

    companion object {
    }

    override fun onCreatePersistentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return binding.root
    }

    override fun onPersistentViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onPersistentViewCreated(view, savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        init()
    }

    private fun init() {
        //binding sets
        binding.viewModel = viewModel
        binding.activity = (activity as MainActivity)
        binding.fragment = this@MainFragment
        binding.temperatureType = TemperatureUnits.CELSIUS
        observeCurrentLocation()
        observeCurrentWeather()
        fetchSavedWeatherList()
        addAdapterToSavedWeatherList()

        binding.buttonSaveWeather.setOnClickListener {
            saveWeatherData(weatherEntity)
        }
        binding.buttonLocation.setOnClickListener {
            //viewModel.requestLocationUpdates()
            viewModel.location.value?.let { location -> getWeatherForLocation(location) }
        }
    }

    fun observeCurrentLocation() {
        viewModel.getLocation().observe(viewLifecycleOwner) {
            if (!isFirstWeatherRetrieved) {
                Log.d(LOG_TAG, "Hello")
                getWeatherForLocation(it)
                isFirstWeatherRetrieved = true
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

    fun saveWeatherData(weatherEntity: WeatherEntity) {
        viewModel.saveWeather(weatherEntity)
    }

    fun getWeatherForLocation(location: Location) {
        viewModel.status.postValue(Status.LOADING)
        viewModel.getWeatherForLocation(location)
    }

    fun fetchSavedWeatherList() {
        viewModel.savedWeatherList.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it.take(5) as MutableList<WeatherEntity>)
        }
        viewModel.getSavedWeather(viewLifecycleOwner)
    }

    fun observeCurrentWeather() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.code != 200) {
                    viewModel.errorMessage.postValue(it.message)
                    viewModel.status.postValue(Status.ERROR)
                } else {
                    binding.buttonLocation.visibility = View.VISIBLE
                    viewModel.status.postValue(Status.INVISIBLE_SUCCESS)
                    binding.weather = it
                    weatherEntity = it
                    Log.d(LOG_TAG,"Newly retrieved weather id is "+it.uniqueId)
                }
            } else {
                viewModel.status.postValue(Status.ERROR)
            }
        }
    }

    override fun onWeatherItemClicked(weather: WeatherEntity) {
        // TODO: handle erase events here
    }
}