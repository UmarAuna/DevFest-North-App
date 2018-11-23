package com.defvest.devfestnorth.di

import android.arch.lifecycle.ViewModelProvider
import com.defvest.devfestnorth.viewmodel.AppViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
