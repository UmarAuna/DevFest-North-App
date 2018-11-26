package com.defvest.devfestnorth.di

import com.defvest.devfestnorth.DevFestNCApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ActivityBindingModule::class,
            ViewModelModule::class
        ])
interface AppComponent : AndroidInjector<DevFestNCApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DevFestNCApp>()
}