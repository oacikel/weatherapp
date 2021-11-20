package com.oacikel.baseapp.binding.listAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.oacikel.baseapp.R
import com.oacikel.baseapp.databinding.ItemWeatherBinding
import com.oacikel.baseapp.db.entity.WeatherEntity
import com.oacikel.baseapp.ui.callback.ListItemFocusCallback

class WeatherAdapter(var focusCallback: ListItemFocusCallback) :
    RecyclerView.Adapter<WeatherAdapter.WeatherListViewHolder>() {
    var weatherList = mutableListOf<WeatherEntity>()

    fun submitList(list: MutableList<WeatherEntity>) {
        weatherList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeatherListViewHolder(
            DataBindingUtil.inflate
                (
                LayoutInflater.from(parent.context),
                R.layout.item_weather,
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: WeatherListViewHolder,
        position: Int
    ) {
        holder.itemWeatherBinding.weather = weatherList[position]

    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    inner class WeatherListViewHolder(
        val itemWeatherBinding: ItemWeatherBinding
    ) :
        RecyclerView.ViewHolder(itemWeatherBinding.root)
}