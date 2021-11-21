package com.oacikel.baseapp.di.module

import com.oacikel.baseapp.ui.MainFragment
import com.oacikel.baseapp.ui.SavedWeatherFragment
import com.oacikel.baseapp.ui.common.ErrorDialogFragment
import com.oacikel.baseapp.ui.common.LoadingDialogFragment
import com.oacikel.baseapp.ui.common.SuccessDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSavedWeatherFragment(): SavedWeatherFragment

    @ContributesAndroidInjector
    internal abstract fun contributeLoadingDialogFragment(): LoadingDialogFragment

    @ContributesAndroidInjector
    internal abstract fun contributeErrorDialogFragment(): ErrorDialogFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSuccessDialogFragment(): SuccessDialogFragment
}