package com.oacikel.baseapp.api

import android.provider.Settings
import androidx.datastore.core.DataStore
import com.oacikel.baseapp.*
import com.oacikel.baseapp.api.request.RequestAccessToken
import com.oacikel.baseapp.api.request.RequestLoginModel
import com.oacikel.baseapp.api.response.ResponseLoginModel
import com.oacikel.baseapp.core.BaseActivity
import com.oacikel.baseapp.db.AppDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class BaseAuth (var apiServiceHolder: ApiServiceHolder? = null,
                var appDatabase: AppDb,
                var appSettingsDataStore: DataStore<AppSettings>,
                var sUserDataStore: DataStore<StaticUser>,
                var userDataStore: DataStore<User>
) : Authenticator {

    var isEndingSession = false


    override fun authenticate(route: Route?, response: Response): Request? {
        if (apiServiceHolder == null) return null

        var rememberMe: Boolean
        var userAccessToken: String
        var sUserRefreshToken: String

        runBlocking {
            rememberMe = appSettingsDataStore.data.first().rememberMe
            userAccessToken = userDataStore.data.first().refreshToken
            sUserRefreshToken = sUserDataStore.data.first().refreshToken
        }
        if(response.request.url.encodedPath.contains("Account/Login")){
            isEndingSession = false
        }

        if (response.request.url.encodedPath.contains("/v1/remote/")) {
            //endRemoteSession()
        }else if(rememberMe){
            //renew auth token via refresh token
            synchronized (this)  {
                val responseModelCall = apiServiceHolder?.baseService?.setAccessToken(
                    RequestAccessToken(userAccessToken,
                        Settings.Secure.getString(BaseApplication.sContext?.contentResolver, Settings.Secure.ANDROID_ID))
                )

                val responseModelResponse = responseModelCall?.execute()
                if (responseModelResponse?.code() == 200 &&
                    responseModelResponse.body()?.data?.content?.access_token != null) {

                    val responseModel = responseModelResponse.body()
                    saveAuthTokens(responseModel?.data?.content!!)

                    return response.request.newBuilder()
                        .header("Authorization", "Bearer " + responseModelResponse.body()?.data?.content?.access_token)
                        .build()
                }else {
                    endSession()
                }
            }
        }else{
            if(!response.request.url.encodedPath.contains("SendVerificationCode")
                && !response.request.url.encodedPath.contains("CheckCode")
                && !response.request.url.encodedPath.contains("Register")
                && !response.request.url.encodedPath.contains("ForgotPasswordReset")){
                //go to login
                endSession()
            }else{
                //renew static auth token via refresh token
                synchronized (this)  {
                    val responseModelCall = apiServiceHolder?.baseService?.requestAccountLogin(
                        RequestLoginModel().apply {
                            this.userName = BuildConfig.BASE_USERNAME
                            this.password = BuildConfig.BASE_PASSWORD
                            this.DeviceId = Settings.Secure.getString(
                                BaseApplication.sContext?.contentResolver,
                                Settings.Secure.ANDROID_ID
                            )
                        }
                    )

                    val responseModelResponse = responseModelCall?.execute()
                    if (responseModelResponse?.code() == 200 &&
                        responseModelResponse.body()?.data?.content?.access_token != null) {

                        val responseModel = responseModelResponse.body()
                        saveStaticAuthTokens(responseModel?.data?.content!!)

                        return response.request.newBuilder()
                            .header("Authorization", "Bearer " + responseModelResponse.body()?.data?.content?.access_token)
                            .build()
                    }
                }
            }
        }
        return null
    }

    private fun endSession() {
        //Shows session end dialog (SessionEndDialogFragment is not imported so will not be used)
        /*
        if(!(BaseApplication.activity as BaseActivity).supportFragmentManager.isDestroyed){
            val showingEnding = (BaseApplication.activity as BaseActivity).supportFragmentManager
                .findFragmentByTag("SessionEndDialogFragment")

            if(showingEnding == null&&!isEndingSession){
                val sessionEndDialogFragment = SessionEndDialogFragment()
                sessionEndDialogFragment.show(
                    (BaseApplication.activity as BaseActivity).supportFragmentManager,
                    "SessionEndDialogFragment"
                )
                isEndingSession = true
            }
        }
         */
    }

    //save user auth to datastore
    private fun saveAuthTokens(responseLoginModel: ResponseLoginModel) {
        GlobalScope.launch(Dispatchers.IO) {
            userDataStore.updateData { user ->
                user.toBuilder()
                    .setAccessToken(responseLoginModel.access_token)
                    .setRefreshToken(responseLoginModel.refresh_token)
                    .build()
            }
        }
    }

    //save user auth to datastore
    private fun saveStaticAuthTokens(responseLoginModel: ResponseLoginModel) {
        GlobalScope.launch(Dispatchers.IO) {
            sUserDataStore.updateData { user ->
                user.toBuilder()
                    .setAccessToken(responseLoginModel.access_token)
                    .setRefreshToken(responseLoginModel.refresh_token)
                    .build()
            }
        }
    }
}