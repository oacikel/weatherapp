package com.oacikel.baseapp.api

import androidx.lifecycle.LiveData
import com.oacikel.baseapp.api.endpoints.RemoteConstants
import com.oacikel.baseapp.api.request.RequestAccessToken
import com.oacikel.baseapp.api.request.RequestLoginModel
import com.oacikel.baseapp.api.response.ResponseLoginModel
import com.oacikel.baseapp.db.entity.UserEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BaseService {
    @GET("social/{userId}/MyProfileDummy")
    fun getMySocialProfile(@Path("userId") userId: String?): LiveData<ApiResponse<UserEntity>>

    @POST(RemoteConstants.SET_ACCESS_TOKEN)
    fun setAccessToken(@Body requestAccessToken: RequestAccessToken): Call<ApiResponse<ResponseLoginModel>>

    @POST(RemoteConstants.LOGIN)
    fun requestAccountLogin(@Body requestLogin: RequestLoginModel): Call<ApiResponse<ResponseLoginModel>>
}