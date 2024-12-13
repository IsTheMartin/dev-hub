package com.mrtnmrls.devhub.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}
