package com.oacikel.baseapp.api

import androidx.lifecycle.LiveData
import com.oacikel.baseapp.api.endpoints.RemoteConstants
import com.oacikel.baseapp.api.request.RequestAccessToken
import com.oacikel.baseapp.api.request.RequestLoginModel
import com.oacikel.baseapp.api.response.ResponseLoginModel
import com.oacikel.baseapp.db.entity.UserEntity
import com.oacikel.baseapp.db.entity.marvelEntities.CharacterEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MarvelService {
    @GET("v1/public/characters")
    fun getCharacters(): Call<ApiResponse<List<CharacterEntity>>>

    @POST(RemoteConstants.SET_ACCESS_TOKEN)
    fun setAccessToken(@Body requestAccessToken: RequestAccessToken): Call<ApiResponse<ResponseLoginModel>>

    @POST(RemoteConstants.LOGIN)
    fun requestAccountLogin(@Body requestLogin: RequestLoginModel): Call<ApiResponse<ResponseLoginModel>>
}