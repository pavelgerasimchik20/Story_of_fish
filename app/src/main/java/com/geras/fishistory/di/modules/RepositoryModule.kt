package com.geras.fishistory.di.modules

import com.geras.fishistory.data.FishRepository
import com.geras.fishistory.data.FishRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindFishRepository(repository: FishRepositoryImpl): FishRepository
}