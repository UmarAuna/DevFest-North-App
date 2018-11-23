package com.defvest.devfestnorth.di

import android.arch.lifecycle.ViewModel
import com.defvest.devfestnorth.viewmodel.MainActivityViewModel
import com.defvest.devfestnorth.viewmodel.ProfileActivityViewModel
import com.defvest.devfestnorth.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileActivityModule{

    @Binds
    @IntoMap
    @ViewModelKey(ProfileActivityViewModel::class)
    internal abstract fun profileActivityViewModel(viewModel: ProfileActivityViewModel): ViewModel
}