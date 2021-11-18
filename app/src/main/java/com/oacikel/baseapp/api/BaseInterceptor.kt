package com.oacikel.baseapp.api

import android.util.Log
import androidx.datastore.core.DataStore
import com.oacikel.baseapp.AppSettings
import com.oacikel.baseapp.BuildConfig
import com.oacikel.baseapp.StaticUser
import com.oacikel.baseapp.User
import com.oacikel.baseapp.api.request.RequestRemote
import com.oacikel.baseapp.util.AESEncryptor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

class BaseInterceptor(
    var userDataStore: DataStore<User>,
    var appSettingsDataStore: DataStore<AppSettings>,
    var sUserDataStore: DataStore<StaticUser>
) : Interceptor {

    val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var langCode: String
        var userAccessToken: String
        var sUserAccessToken: String
        var remoteAccessToken: String
        val apiKey: String = BuildConfig.MARVEL_API_PUBLIC_KEY
        val secretApiKey:String =BuildConfig.MARVEL_API_PRIVATE_KEY
        var ts:String=""
        runBlocking {
            langCode = appSettingsDataStore.data.first().language
            if (langCode.isEmpty())
                appSettingsDataStore.updateData { appSettings ->
                    appSettings.toBuilder()
                        .setLanguage(Locale.getDefault().language)
                        .build()
                }

            userAccessToken = userDataStore.data.first().accessToken
            remoteAccessToken = userDataStore.data.first().remoteAccessToken
            sUserAccessToken = sUserDataStore.data.first().accessToken
        }
        fun md5(input:String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
        fun getTime():String{
            ts=System.currentTimeMillis().toString()
            return ts
        }

        val httpUrl = request.url.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .addQueryParameter("ts",getTime())
            .addQueryParameter("hash",md5(ts+secretApiKey+apiKey))
            .build()
        request = request.newBuilder().url(httpUrl).build()

        //move out gradle file
        val secretKey =
            BuildConfig.BASE_SECRET_KEY
        var encryptor = AESEncryptor()
        var reqBody: RequestBody? = null
        //
        if (request.body != null && !BuildConfig.DEBUG
            && (!request.url.encodedPath.contains("UpdateProfilePicture")
                    && !request.url.encodedPath.contains("PostMedia"))
        ) { //&& !BuildConfig.DEBUG
            val encryptedBody: String? = encryptor.encrypt(request.body!!.bodyToString(), secretKey)

            var requestRemoteModel = RequestRemote(encryptedBody)

            reqBody = RequestBody.create(JSON, requestRemoteModel.toString())
        }


        if (!request.url.encodedPath.contains("Login/LoginAsync")
            && !request.url.encodedPath.contains("Login/LoginWithToken")
            && !request.url.encodedPath.contains("Account/Login")
        ) {


            if (request.url.encodedPath.contains("/v1/remote/") && !request.url.encodedPath.contains(
                    "/v1/remote/ChangeEcoModeStatus"
                )
            ) {


                if (reqBody != null)
                    request = request.newBuilder()
                        .post(reqBody)
                        .addHeader("Authorization", "Bearer " + remoteAccessToken)
                        .build()
                else
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + remoteAccessToken)
                        .build()

            } else if (request.url.encodedPath.contains("GetVersionInfo") ||
                request.url.encodedPath.contains("crmuser/GetUserInformationsAsync") ||
                request.url.encodedPath.contains("crmuser/SendVerificationCode") ||
                request.url.encodedPath.contains("user/CheckSMS") ||
                request.url.encodedPath.contains("Account/CheckCode") ||
                request.url.encodedPath.contains("crmuser/UserRegisterGetData") ||
                request.url.encodedPath.contains("user/ForgotPasswordReset") ||
                request.url.encodedPath.contains("Account/ForgotPasswordReset") ||
                request.url.encodedPath.contains("contract/approve") ||
                request.url.encodedPath.contains("RegisterUserContract") ||
                request.url.encodedPath.contains("Register") ||
                request.url.encodedPath.contains("SetContractStatus") ||
                // request.url().encodedPath().contains("Report/CustomerVehicle") ||
                request.url.encodedPath.contains("GetContract")
            ) {

                if (userAccessToken.isEmpty()) {
                    if (reqBody != null)
                        request = request.newBuilder()
                            .post(reqBody)
                            .addHeader("Authorization", "Bearer " + sUserAccessToken)
                            .build()
                    else
                        request = request.newBuilder()
                            .addHeader("Authorization", "Bearer " + sUserAccessToken)
                            .build()


                } else {

                    if (reqBody != null)
                        request = request.newBuilder()
                            .post(reqBody)
                            .addHeader("Authorization", "Bearer " + userAccessToken)
                            .build()
                    else
                        request = request.newBuilder()
                            .addHeader("Authorization", "Bearer " + userAccessToken)
                            .build()
                }

            } else {
                //tekrar
                if (reqBody != null)
                    request = request.newBuilder()
                        .post(reqBody)
                        .addHeader("Authorization", "Bearer " + userAccessToken)
                        .build()
                else
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + userAccessToken)
                        .build()

            }
        }

        //tekrar
        if (reqBody != null)
            request = request.newBuilder()
                .post(reqBody).build()


        var requestFormat = String.format(
            "Sending request %s on body: %s%n%s%n%s",
            request.url, chain.connection(), toDoString(request), request.headers
        )


        var response = chain.proceed(request)


        if (response.code == 200) {
            val body = response.body!!

            val contentType = body.contentType()
            val charset = contentType?.charset() ?: Charset.defaultCharset()
            val buffer = body.source().apply { request(Long.MAX_VALUE) }.buffer()
            var bodyContent = buffer.clone().readString(charset)
            var jsonObject = JSONObject(bodyContent)

            var remoteResponse: String? = null



            try {
                remoteResponse = jsonObject.getString("RemoteResponse")
            } catch (e: JSONException) {
                Log.d("INTERCEPTER", "JSONException")
            } catch (e: Exception) {
                Log.d("INTERCEPTER", "Exception")
            }

            if (remoteResponse != null) { //gelen response şifreli ise
                val decryptedBody: String? = encryptor.decryptWithAES(secretKey, remoteResponse)


                return response.newBuilder()
                    .body(ResponseBody.create(JSON, decryptedBody!!))
                    .build()
            } else
                return response //gelen response şifresiz ise

        } else { // hata
            return response
        }

    }

    fun RequestBody?.bodyToString(): String {
        if (this == null) return ""
        val buffer = okio.Buffer()
        writeTo(buffer)
        return buffer.readUtf8()
    }

    companion object {
        fun toDoString(request: Request): String? {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                if (copy.body == null)
                    return null
                copy.body!!.writeTo(buffer)
                return buffer.readUtf8()
            } catch (e: IOException) {
                return e.message
            }
        }
    }
}


const val getListOfLikesJson = """
    {
  "meta": {},
  "data": {
    "content": [
      {
        "id": "1",
        "userName": "Fernando",
        "userSurname": "Muslera",
        "userImageUrl": "https://img.a.transfermarkt.technology/portrait/header/58088-1572949088.jpg?lm=1",
        "userVehicleCount": 1,
        "userFollowState":0
      },
      {
        "id": "9",
      "userName": "Johan",
      "userSurname": "Elmander",
      "userImageUrl":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTiQoFopqC16jh55WbQ-yYvAwHF0DUlGHNKA&usqp=CAU",
      "userVehicleCount": 9,
      "userFollowState":1
      },
      {
        "id": "7",
      "userName": "Henry",
      "userSurname": "Onyekuru",
      "userImageUrl":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzGBB9O96EWp2Qs9ZZY_7yRswGYAbjOTqK6Q&usqp=CAU",
      "userVehicleCount": 7,
      "userFollowState":2
      }
    ]
  },
  "logData": {},
  "traceData": [],
  "isFaulted": false,
  "isFault": false
}
"""

const val getUserProfileJson = """
    
    {
  "meta": {},
  "data": {
    "content": {
  "id": "2",
  "userName": "Fernando",
  "userSurname": "Muslera",
  "userImageUrl": "https://img.a.transfermarkt.technology/portrait/header/58088-1572949088.jpg?lm=1",
  "userIsApproved": false,
  "userVehicleCount": 9,
  "userPostCount": 291,
  "userFollowerCount": 4,
  "userFollowingCount": 41,
  "userFollowState":1,
  "postList": [
    {
      "id": "1296279",
      "user": {
      "id": "1",
      "userName": "Fernando",
      "userSurname": "Muslera",
      "userImageUrl":"https://img.a.transfermarkt.technology/portrait/header/58088-1572949088.jpg?lm=1",
      "userIsApproved":false
    },
      "mediaList": [],
      "body": "Lorem ipsum dolor sit amet",
      "createdOn": "2021-03-21T14:10:46.384Z",
      "likeCount": 230,
      "isMyPost": true,
      "isMyLike": false
    },
    {
      "id": "1296289",
      "user": {
      "id": "1",
      "userName": "Fernando",
      "userSurname": "Muslera",
      "userImageUrl":"https://img.a.transfermarkt.technology/portrait/header/58088-1572949088.jpg?lm=1",
      "userIsApproved":false
    },
      "mediaList": [
        {
          "mediaUrl": "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/b2ea5e75329623.5c49848d80871.png"
        }
      ],
      "body": "",
      "createdOn": "2021-03-21T17:17:46.384Z",
      "likeCount": 39,
      "isMyPost": true,
      "isMyLike": false
    },
    {
      "id": "1296269",
      "user": {
      "id": "1",
      "userName": "Fernando",
      "userSurname": "Muslera",
      "userImageUrl":"https://img.a.transfermarkt.technology/portrait/header/58088-1572949088.jpg?lm=1",
      "userIsApproved":false
    },
      "mediaList": [],
      "body": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet auctor posuere. Mauris odio lorem, lobortis eu lobortis id, elementum id est. Sed vel consectetur sem, eu pretium nulla. Curabitur mollis ex tortor, ut tempor mi faucibus vel.",
      "createdOn": "2021-03-21T10:14:46.384Z",
      "likeCount": 12,
      "isMyPost": true,
      "isMyLike": false
    },
    {
      "id": "1296389",
      "user": {
      "id": "1",
      "userName": "Fernando",
      "userSurname": "Muslera",
      "userImageUrl":"https://img.a.transfermarkt.technology/portrait/header/58088-1572949088.jpg?lm=1",
      "userIsApproved":false
    },
      "mediaList": [
        {
          "mediaUrl": "https://blog.ford.com.tr/uploads/2018/10/f-max--26.png"
        }
      ],
      "body": "",
      "createdOn": "2021-03-21T17:17:46.384Z",
      "likeCount": 57,
      "isMyPost": true,
      "isMyLike": false
    }
  ]
}
  },
  "logData": {},
  "traceData": [],
  "isFaulted": false,
  "isFault": false
}
"""

const val getVersionInfoJson = """
    {
    "meta": {},
    "data": {
    "content": {
    "optional": true,
    "force": false,
    "environment": "UAT"
}
},
    "logData": {},
    "traceData": [],
    "isFaulted": false
}
"""
