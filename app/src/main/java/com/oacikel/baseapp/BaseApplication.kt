package com.oacikel.baseapp

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.oacikel.baseapp.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector, HasSupportFragmentInjector,
    HasServiceInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mServiceInjector: DispatchingAndroidInjector<Service>


    override fun onCreate() {
        super.onCreate()
        /*if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }*/
        // This flag should be set to true to enable VectorDrawable support for API < 21.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        instance = this
        sContext = this
        AppInjector.init(this)

        sInstance = this
    }
    fun currentActivity(currentActivity: Activity){
        activity = currentActivity
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return mFragmentInjector
    }

    companion object {
        lateinit var sContext: Context
        lateinit var instance: BaseApplication
        lateinit var activity: Activity
        private var sInstance: BaseApplication? = null
        var isBackCount: Int = 0

        fun get(): BaseApplication? {
            return sInstance
        }
    }

    override fun serviceInjector(): DispatchingAndroidInjector<Service>? {
        return mServiceInjector
    }
}
