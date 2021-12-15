package com.geras.fishistory.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module(includes = [RepositoryModule::class, DataBaseModule::class])
interface AppModule {

    @Binds
    fun application(app: Application): Context
}