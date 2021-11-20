package com.oacikel.baseapp.api

import com.oacikel.baseapp.api.endpoints.RemoteConstants
import com.oacikel.baseapp.api.request.RequestAccessToken
import com.oacikel.baseapp.api.request.RequestLoginModel
import com.oacikel.baseapp.api.response.ResponseLoginModel
import com.oacikel.baseapp.db.entity.WeatherEntity
import retrofit2.Call
import retrofit2.http.*

interface BaseService {
    @GET(RemoteConstants.WEATHER_DATA)
    fun getWeatherForCity(@Query("q") cityName: String?,@Query("units") units: String?): Call<WeatherEntity>

    @GET(RemoteConstants.WEATHER_DATA)
    fun getWeatherForLocation(@Query("lat") latitude: String?,@Query("lon") longitude: String?,@Query("units") units: String?): Call<WeatherEntity>


    @POST(RemoteConstants.SET_ACCESS_TOKEN)
    fun setAccessToken(@Body requestAccessToken: RequestAccessToken): Call<ApiResponse<ResponseLoginModel>>

    @POST(RemoteConstants.LOGIN)
    fun requestAccountLogin(@Body requestLogin: RequestLoginModel): Call<ApiResponse<ResponseLoginModel>>
}