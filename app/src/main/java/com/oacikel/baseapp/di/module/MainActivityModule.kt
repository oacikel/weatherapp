package com.oacikel.baseapp.di.module

import com.oacikel.baseapp.MainActivity
import com.oacikel.baseapp.di.module.FragmentBuildersModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}