package com.oacikel.baseapp.di.module

import androidx.datastore.core.DataStore
import com.oacikel.baseapp.*
import com.oacikel.baseapp.api.*
import com.oacikel.baseapp.db.AppDb
import com.oacikel.baseapp.util.LiveDataCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    private val CACHE_SIZE = 10L
    private val CACHE_MEMORY_SIZE = 1024L
    private val TIMEOUT_SESSION = 60L

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun apiServiceHolder(): ApiServiceHolder {
        return ApiServiceHolder()
    }

    /*
    @Singleton
    @Provides
    fun providePinner(app: BaseApplication): CertificatePinner {
        val peerCertificate = PeerCertificateExtractor.extract(BaseApplication.sContext)
        return CertificatePinner.Builder().add(RemoteConstants.CERTIFICATE_HOST, peerCertificate)
            .build()
    }
*/
    @Singleton
    @Provides
    fun provideAuthantiacate(
        apiServiceHolder: ApiServiceHolder,
        appDatabase: AppDb,
        appSettingsDataStore: DataStore<AppSettings>,
        sUserDataStore: DataStore<StaticUser>,
        userDataStore: DataStore<User>
    ): BaseAuth {
        return BaseAuth(
            apiServiceHolder,
            appDatabase,
            appSettingsDataStore,
            sUserDataStore,
            userDataStore
        )
    }

    @Provides
    @Singleton
    fun provideCache(app: BaseApplication): Cache {
        // 10 MB Cache for Network Unavailable
        return Cache(
            File(BaseApplication.sContext.cacheDir, "https"),
            CACHE_SIZE * CACHE_MEMORY_SIZE * CACHE_MEMORY_SIZE
        )
    }

    @Singleton
    @Provides

    fun provideBaseService(
        @Named("APP_SERVICE_RETROFIT") retrofit: Retrofit,
        apiServiceHolder: ApiServiceHolder
    ): BaseService {
        val baseService = retrofit.create(BaseService::class.java)
        apiServiceHolder.baseService = baseService
        return baseService
    }

    @Provides
    @Singleton
    @Named("MockInterceptor")
    fun provideMockInterceptor(): Interceptor = MockInterceptor()

    @Singleton
    @Provides
    fun provideInterceptor(
        userDataStore: DataStore<User>,
        appSettingsDataStore: DataStore<AppSettings>,
        sUserDataStore: DataStore<StaticUser>
    ): BaseInterceptor {
        return BaseInterceptor(
            userDataStore,
            appSettingsDataStore,
            sUserDataStore
        )
    }

    @Singleton
    @Provides
    fun provideOKHttpClient(
        dispatcher: Dispatcher,
        myFordInterceptor: BaseInterceptor,
        cache: Cache,
        //certificatePinner: CertificatePinner,
        auth: BaseAuth
    ): OkHttpClient {


        dispatcher.maxRequests = 4

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        var cookieManager = CookieManager()

        cookieManager.cookieStore.removeAll()

        return OkHttpClient.Builder()
            .cache(cache)
            .cookieJar(JavaNetCookieJar(cookieManager))
            //.certificatePinner(certificatePinner)
            .addInterceptor(myFordInterceptor)
            .addInterceptor(interceptor)
            .addNetworkInterceptor(ChuckInterceptor(BaseApplication.sContext))
            .authenticator(auth)
            .connectTimeout(TIMEOUT_SESSION, TimeUnit.HOURS)
            .writeTimeout(TIMEOUT_SESSION, TimeUnit.HOURS)
            .readTimeout(TIMEOUT_SESSION, TimeUnit.HOURS)
            .dispatcher(dispatcher)
            .build()
    }

    @Singleton
    @Provides
    @Named("APP_SERVICE_RETROFIT")
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("MARVEL_SERVICE_RETROFIT")
    fun provideMarvelRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}