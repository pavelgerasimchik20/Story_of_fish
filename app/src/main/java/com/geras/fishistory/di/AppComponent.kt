package com.geras.fishistory.di

import android.app.Application
import com.geras.fishistory.di.factory.ViewModelFactory
import com.geras.fishistory.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}