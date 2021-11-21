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

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var langCode: String
        val apiKey: String = BuildConfig.OPEN_WEATHER_API
        runBlocking {
            langCode = appSettingsDataStore.data.first().language
            if (langCode.isEmpty())
                appSettingsDataStore.updateData { appSettings ->
                    appSettings.toBuilder()
                        .setLanguage(Locale.getDefault().language)
                        .build()
                }
        }

        val httpUrl = request.url.newBuilder()
            .addQueryParameter("appid", apiKey)
            .build()
        request = request.newBuilder().url(httpUrl).build()



        var response = chain.proceed(request)


        if (response.code == 200) {
            val body = response.body!!

            val contentType = body.contentType()
            val charset = contentType?.charset() ?: Charset.defaultCharset()
            val buffer = body.source().apply { request(Long.MAX_VALUE) }.buffer()
            var bodyContent = buffer.clone().readString(charset)

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

"""

const val getUserProfileJson = """
    
 
"""

const val getVersionInfoJson = """
   
"""
