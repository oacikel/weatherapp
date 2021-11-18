package com.oacikel.baseapp.di

import android.app.Application
import com.oacikel.baseapp.BaseApplication
import com.oacikel.baseapp.di.module.ActivityModule
import com.oacikel.baseapp.di.module.AppModule
import com.oacikel.baseapp.di.module.FragmentBuildersModule
import com.oacikel.baseapp.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    AndroidInjectionModule::class,
    NetworkModule::class,
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    FragmentBuildersModule::class
])


interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(myApp: BaseApplication)
}