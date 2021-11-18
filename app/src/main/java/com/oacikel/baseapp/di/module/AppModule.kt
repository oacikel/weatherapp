package com.oacikel.baseapp.di.module

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.oacikel.baseapp.AppSettings
import com.oacikel.baseapp.BaseApplication
import com.oacikel.baseapp.StaticUser
import com.oacikel.baseapp.User
import com.oacikel.baseapp.db.AppDb
import com.oacikel.baseapp.db.dao.UserDao
import com.oacikel.baseapp.db.dataStore.AppSettingsSerializer
import com.oacikel.baseapp.db.dataStore.StaticUserSerializer
import com.oacikel.baseapp.db.dataStore.UserSerializer
import dagger.Module
import dagger.Provides
import okhttp3.Dispatcher
import java.io.File
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
internal class AppModule {
    companion object {
        private const val KEYSET_NAME = "master_keyset"
        private const val PREFERENCE_FILE = "master_key_preference"
        private const val MASTER_KEY_URI = "android-keystore://master_key"

        private const val USER_DATASTORE_FILE = "user.pb"
        private const val STATIC_USER_DATASTORE_FILE = "suser.pb"
        private const val APP_SETTINGS_DATASTORE_FILE = "settings.pb"
    }

    @Singleton
    @Provides
    fun provideAead(application: Application): Aead {
        AeadConfig.register()

        return AndroidKeysetManager.Builder()
            .withSharedPref(application, KEYSET_NAME, PREFERENCE_FILE)
            .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
            .getPrimitive(Aead::class.java)
    }
    @Singleton
    @Provides
    fun provideUserDataStore(application: Application, aead: Aead): DataStore<User> {
        return DataStoreFactory.create(
            produceFile = { File(application.filesDir, "datastore/$USER_DATASTORE_FILE") },
            serializer = UserSerializer(aead)
        )
    }
    @Singleton
    @Provides
    fun provideStaticUserDataStore(application: Application, aead: Aead): DataStore<StaticUser> {
        return DataStoreFactory.create(
            produceFile = { File(application.filesDir, "datastore/$STATIC_USER_DATASTORE_FILE") },
            serializer = StaticUserSerializer(aead)
        )
    }
    @Singleton
    @Provides
    fun provideAppSettingsDataStore(application: Application, aead: Aead): DataStore<AppSettings> {
        return DataStoreFactory.create(
            produceFile = { File(application.filesDir, "datastore/$APP_SETTINGS_DATASTORE_FILE") },
            serializer = AppSettingsSerializer(aead)
        )
    }

    @Singleton
    @Provides
    fun getInstance(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(BaseApplication.sContext)
    }


    @Singleton
    @Provides
    fun provideDb(app: Application): AppDb {
        return Room.databaseBuilder(app, AppDb::class.java, "base-app-db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: AppDb): UserDao {
        return db.userDao()
    }


    @Singleton
    @Provides
    fun provideDispatcher(): Dispatcher {
        return Dispatcher()
    }

    @Singleton
    @Provides
    fun provideApplication(): BaseApplication {
        return BaseApplication()
    }

}