package com.oacikel.baseapp.binding

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oacikel.baseapp.R
import com.oacikel.baseapp.binding.listAdapters.WeatherAdapter
import com.oacikel.baseapp.db.entity.WeatherEntity
import com.oacikel.baseapp.db.enums.TemperatureUnits
import com.oacikel.baseapp.db.enums.eWeatherTypes
import com.oacikel.baseapp.util.getCountryName

object ViewAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(
        value = ["detailViewGroup"]
    )
    fun switchDetails(textView: TextView, detailView: View) {
        textView.setOnClickListener {
            if (textView.text == textView.context.getString(R.string.main_fragment_more_details)) {
                detailView.visibility = View.VISIBLE
                textView.setText(textView.context.getString(R.string.main_fragment_less_details))
            } else if (textView.text == textView.context.getString(R.string.main_fragment_less_details)) {
                detailView.visibility = View.GONE
                textView.setText(textView.context.getString(R.string.main_fragment_more_details))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter(
        value = ["weatherLocation"]
    )
    fun locationText(textView: TextView, weather: WeatherEntity?) {
        textView.text =
            weather?.locationDetail?.countryCode?.getCountryName() + " - " + weather?.city
    }

    @JvmStatic
    @BindingAdapter(
        value = ["weatherIconForBackground"]
    )
    fun setBackground(view: ImageView, weatherStat: String?) {

        eWeatherTypes.values().forEach {
            if (it.code == weatherStat) {
                view.setImageResource(it.referenceImage)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["weatherForTemperature", "temperatureType"]
    )
    fun setTempUnit(view: TextView, weather: WeatherEntity?, type: TemperatureUnits?) {
        var degree = weather?.detail?.temperature.toString()
        when (type) {
            TemperatureUnits.FAHRENHEIT -> {
                degree += " °F"
            }
            TemperatureUnits.CELSIUS -> {
                degree += " ℃"
            }
            TemperatureUnits.KELVIN -> {
                degree += " K"
            }
        }
        view.text = degree
    }

    @JvmStatic
    @BindingAdapter(
        value = ["listView", "adapter"]
    )
    fun setEmptyListPage(emptyMessageView: View, listView: View, adapter: WeatherAdapter?) {
        Log.d("OCUL", "Hi:")
        Log.d("OCUL", "Item count is " + adapter?.itemCount)
        if (adapter?.itemCount != 0) {
            listView.visibility = View.VISIBLE
            emptyMessageView.visibility = View.GONE
        } else {
            listView.visibility = View.GONE
            emptyMessageView.visibility = View.VISIBLE
        }
    }

}