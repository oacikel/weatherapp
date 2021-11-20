package com.oacikel.baseapp.db.enums

import com.oacikel.baseapp.R

enum class eWeatherTypes(var code: String,var description: String, var referenceImage: Int){
    clear_sky_day("01d","Clear Sky", R.drawable.image_clear_sky_day),
    few_clouds_day("02d","Few Clouds",R.drawable.image_few_clouds_day),
    scattered_clouds_day("03d","Scattered Clouds", R.drawable.image_scattered_clouds_day),
    broken_clouds_day("04d","Broken Clouds",R.drawable.image_broken_clouds_day),
    shower_rain_day("09d","Shower Rain", R.drawable.image_shower_rain_day),
    rain_day("10d","Rain",R.drawable.image_rain_day),
    thunder_storm_day("11d","ThunderStorm", R.drawable.image_thunder_storm_day),
    snow_day("13d","Snow",R.drawable.image_snow_day),
    mist_day("50d","Mist", R.drawable.image_mist_day),
    clear_sky_night("01n","Clear Sky",R.drawable.image_clear_sky_night),
    few_clouds_night("02n","Few Clouds", R.drawable.image_few_clouds_night),
    scattered_clouds_night("03n","Scattered Clouds",R.drawable.image_scattered_clouds_night),
    broken_clouds_night("04n","Broken Clouds", R.drawable.image_broken_clouds_night),
    shower_rain_night("09n","Shower Rain",R.drawable.image_shower_rain_night),
    rain_night("10n","Rain", R.drawable.image_rain_night),
    thunder_storm_night("11n","Thunder Storm",R.drawable.image_thunder_storm_night),
    snow_night("13n","Snow", R.drawable.image_snow_night),
    mist_night("50n","Mist",R.drawable.image_mist_night),
}