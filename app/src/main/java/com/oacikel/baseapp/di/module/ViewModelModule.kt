package com.oacikel.baseapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oacikel.baseapp.di.ViewModelKey
import com.oacikel.baseapp.viewModel.BaseViewModelFactory
import com.oacikel.baseapp.viewModel.CommonDialogViewModel
import com.oacikel.baseapp.viewModel.MainViewModel
import com.oacikel.baseapp.viewModel.SavedWeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SavedWeatherViewModel::class)
    abstract fun bindSavedWeatherViewModel(savedWeatherViewModel: SavedWeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommonDialogViewModel::class)
    abstract fun bindCommonDialogViewModel(commonDialogViewModel: CommonDialogViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory
}
