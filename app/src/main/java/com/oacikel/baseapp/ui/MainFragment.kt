package com.oacikel.baseapp.ui

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.oacikel.baseapp.MainActivity
import com.oacikel.baseapp.R
import com.oacikel.baseapp.api.Status
import com.oacikel.baseapp.core.BaseInjectableFragment
import com.oacikel.baseapp.databinding.FragmentMainBinding
import com.oacikel.baseapp.db.entity.WeatherEntity
import com.oacikel.baseapp.db.enums.TemperatureUnits
import com.oacikel.baseapp.di.Injectable
import com.oacikel.baseapp.ui.callback.ListItemFocusCallback
import com.oacikel.baseapp.viewModel.MainViewModel


class MainFragment : BaseInjectableFragment<MainViewModel, FragmentMainBinding>(),
    Injectable {

    override val layoutResourceId: Int = R.layout.fragment_main
    override val viewModelClass: Class<MainViewModel> = MainViewModel::class.java
    private lateinit var weatherEntity: WeatherEntity
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

        binding.buttonSaveWeather.setOnClickListener {
            saveWeatherData(weatherEntity)
            it.visibility=View.GONE
        }
        binding.buttonLocation.setOnClickListener {
            viewModel.location.value?.let { location -> getWeatherForLocation(location) }
        }
    }

    fun observeCurrentLocation() {
        viewModel.getLocation().observe(viewLifecycleOwner) {
            if (!isFirstWeatherRetrieved) {
                getWeatherForLocation(it)
                isFirstWeatherRetrieved = true
            }
        }
    }

    fun saveWeatherData(weatherEntity: WeatherEntity) {
        viewModel.saveWeather(weatherEntity)
    }

    fun getWeatherForLocation(location: Location) {
        viewModel.status.postValue(Status.LOADING)
        viewModel.getWeatherForLocation(location)
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
                    binding.buttonSaveWeather.visibility=View.VISIBLE
                }
            } else {
                viewModel.status.postValue(Status.ERROR)
            }
        }
    }
}