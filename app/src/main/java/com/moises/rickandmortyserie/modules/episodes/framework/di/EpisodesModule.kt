package com.moises.rickandmortyserie.modules.episodes.framework.di

import android.content.Context
import com.moises.rickandmortyserie.modules.episodes.data.datasource.RemoteEpisodesDataSource
import com.moises.rickandmortyserie.modules.episodes.data.repository.EpisodesRepositoryImpl
import com.moises.rickandmortyserie.modules.episodes.domain.repository.EpisodesRepository
import com.moises.rickandmortyserie.modules.episodes.framework.data.EpisodesEndPoint
import com.moises.rickandmortyserie.modules.episodes.framework.data.RemoteEpisodesDataSourceImpl
import com.moises.rickandmortyserie.modules.episodes.framework.res.StringResources
import com.moises.rickandmortyserie.modules.episodes.framework.res.StringResourcesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object EpisodesModule {
    @Provides
    @Singleton
    fun providesEpisodesEndPoint(retrofit: Retrofit): EpisodesEndPoint =
        retrofit.create(EpisodesEndPoint::class.java)

    @Provides
    @Singleton
    fun providesEpisodesRemoteDataSource(episodesEndPoint: EpisodesEndPoint): RemoteEpisodesDataSource =
        RemoteEpisodesDataSourceImpl(episodesEndPoint)

    @Provides
    @Singleton
    fun providesEpisodesRepository(remoteEpisodesDataSource: RemoteEpisodesDataSource): EpisodesRepository =
        EpisodesRepositoryImpl(remoteEpisodesDataSource)

    @Provides
    @Singleton
    fun providesStringResources(@ApplicationContext context : Context): StringResources =
        StringResourcesImpl(context)
}