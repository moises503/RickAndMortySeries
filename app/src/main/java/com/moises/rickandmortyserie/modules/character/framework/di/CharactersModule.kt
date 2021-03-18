package com.moises.rickandmortyserie.modules.character.framework.di

import com.moises.rickandmortyserie.modules.character.data.datasource.CharacterRepositoryImpl
import com.moises.rickandmortyserie.modules.character.data.repository.RemoteCharacterDataSource
import com.moises.rickandmortyserie.modules.character.domain.repository.CharacterRepository
import com.moises.rickandmortyserie.modules.character.framework.data.CharactersEndPoint
import com.moises.rickandmortyserie.modules.character.framework.data.RemoteCharacterDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersModule {

    @Provides
    @Singleton
    fun providesCharactersEndPoint(retrofit: Retrofit): CharactersEndPoint =
        retrofit.create(CharactersEndPoint::class.java)

    @Provides
    @Singleton
    fun providesCharactersRemoteDataSource(charactersEndPoint: CharactersEndPoint): RemoteCharacterDataSource =
        RemoteCharacterDataSourceImpl(charactersEndPoint)

    @Provides
    @Singleton
    fun providesCharactersRepository(remoteCharacterDataSource: RemoteCharacterDataSource): CharacterRepository =
        CharacterRepositoryImpl(remoteCharacterDataSource)
}