package com.oacikel.baseapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oacikel.baseapp.AppExecutors
import com.oacikel.baseapp.api.ApiResponse
import com.oacikel.baseapp.api.BaseService
import com.oacikel.baseapp.db.dao.UserDao
import com.oacikel.baseapp.db.entity.UserEntity
import com.oacikel.baseapp.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val userDao: UserDao,
    private val baseService: BaseService,
) {
    fun getUserFromDb(userGuid: String): UserEntity {
        return userDao.findByGuid(userGuid)
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

    /**
    fun requestUserInvite(
        requestModel: RequestUserInvite,
        responseLiveData: MutableLiveData<ApiResponse<UserInviteResponseModel>>
    ) {
        baseService.userInvite(requestModel)
            .enqueue(object : Callback<ApiResponse<UserInviteResponseModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<UserInviteResponseModel>>,
                    response: Response<ApiResponse<UserInviteResponseModel>>
                ) {
                    responseLiveData.postValue(response.body())
                }

                override fun onFailure(
                    call: Call<ApiResponse<UserInviteResponseModel>>,
                    throwable: Throwable
                ) {
                    responseLiveData.postValue(ApiResponse(throwable))
                }
            })
    }
    */

}
