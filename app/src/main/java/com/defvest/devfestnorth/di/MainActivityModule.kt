package com.defvest.devfestnorth.di

import androidx.lifecycle.ViewModel
import com.defvest.devfestnorth.viewmodel.MainActivityViewModel
import com.defvest.devfestnorth.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class  MainActivityModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun homeViewModel(viewModel: MainActivityViewModel): ViewModel
}