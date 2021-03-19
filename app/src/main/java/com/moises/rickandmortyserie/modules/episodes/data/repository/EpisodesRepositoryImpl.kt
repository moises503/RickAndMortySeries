package com.moises.rickandmortyserie.modules.episodes.data.repository

import com.moises.rickandmortyserie.modules.episodes.data.datasource.RemoteEpisodesDataSource
import com.moises.rickandmortyserie.modules.episodes.domain.model.AllEpisodes
import com.moises.rickandmortyserie.modules.episodes.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(private val remoteEpisodesDataSource: RemoteEpisodesDataSource) : EpisodesRepository {
    override  fun retrieveAllEpisodes(page: Int): Flow<AllEpisodes> = flow {
        emit(remoteEpisodesDataSource.retrieveAllEpisodes(page))
    }
}