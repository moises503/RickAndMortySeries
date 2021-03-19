package com.moises.rickandmortyserie.core.di

import android.content.Context
import com.moises.rickandmortyserie.core.assets.AppResourceManager
import com.moises.rickandmortyserie.core.assets.ResourceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AssetModule {
    @Provides
    @Singleton
    fun providesResourceManager(@ApplicationContext context: Context): ResourceManager =
        AppResourceManager(context)
}