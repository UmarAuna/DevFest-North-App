package com.defvest.devfestnorth.di

import com.defvest.devfestnorth.activities.ProfileActivity
import com.defvest.devfestnorth.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ProfileActivityModule::class])
    internal abstract fun profileActivity(): ProfileActivity

}
