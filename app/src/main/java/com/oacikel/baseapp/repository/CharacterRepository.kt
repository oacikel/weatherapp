package com.oacikel.baseapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oacikel.baseapp.AppExecutors
import com.oacikel.baseapp.api.ApiResponse
import com.oacikel.baseapp.api.BaseService
import com.oacikel.baseapp.api.MarvelService
import com.oacikel.baseapp.api.Resource
import com.oacikel.baseapp.db.dao.UserDao
import com.oacikel.baseapp.db.entity.UserEntity
import com.oacikel.baseapp.db.entity.marvelEntities.CharacterEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val marvelService: MarvelService,
) {
    fun getCharactersWithoutSaving(charactersLiveData:MutableLiveData<ApiResponse<List<CharacterEntity>>>){
        marvelService.getCharacters().enqueue(object : Callback<ApiResponse<List<CharacterEntity>>>{
            override fun onResponse(
                call: Call<ApiResponse<List<CharacterEntity>>>,
                response: Response<ApiResponse<List<CharacterEntity>>>
            ) {
                charactersLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<ApiResponse<List<CharacterEntity>>>, t: Throwable) {
                charactersLiveData.postValue(ApiResponse(t))
            }

        })
    }

    /**
    fun sendVerification(
    requestModel: RequestSendVerificationCode,
    responseLiveData: MutableLiveData<ApiResponse<ResponseSendSMS>>
    ) {
    baseService.requestSendVerification(requestModel)
    .enqueue(object : Callback<ApiResponse<ResponseSendSMS>> {
    override fun onResponse(
    call: Call<ApiResponse<ResponseSendSMS>>,
    response: Response<ApiResponse<ResponseSendSMS>>
    ) {
    responseLiveData.postValue(response.body())
    }

    override fun onFailure(
    call: Call<ApiResponse<ResponseSendSMS>>,
    throwable: Throwable
    ) {
    responseLiveData.postValue(ApiResponse(throwable))
    }
    })
    }
     **/

    /*
    fun loadHomePage(
        date: String?,
        location: String?,
        isConnect: Boolean
    ): LiveData<Resource<HomeEntity>> {
        return object : LoyaltyNetworkBoundResource<HomeEntity, ResponseResultModel<HomeEntity>>(
            appExecutors,
            isConnect
        ) {
            override fun saveCallResult(item: ResponseResultModel<HomeEntity>) {
                //save result
                homeDao.saveHomePage(item.result)
            }

            override fun shouldFetch(data: HomeEntity?): Boolean {
                //fetch it
                return isConnect&&(location!=null)
            }

            override fun loadFromDb(): LiveData<HomeEntity> {
                //createUser(userPostsRequestModel.userId.toString())
                return homeDao.homePage
            }

            override fun createCall() = loyaltyService.requestHomepage(date,location)
        }.asLiveData()
    }

     */

}
