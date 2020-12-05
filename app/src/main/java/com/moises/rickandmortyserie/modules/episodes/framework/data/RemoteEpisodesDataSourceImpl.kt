package com.moises.rickandmortyserie.modules.episodes.framework.data

import com.moises.rickandmortyserie.modules.episodes.data.datasource.RemoteEpisodesDataSource
import com.moises.rickandmortyserie.modules.episodes.domain.model.AllEpisodes
import com.moises.rickandmortyserie.modules.episodes.framework.util.toAllEpisodes
import javax.inject.Inject

class RemoteEpisodesDataSourceImpl @Inject constructor(private val episodesEndPoint: EpisodesEndPoint) : RemoteEpisodesDataSource {
    override suspend fun retrieveAllEpisodes(page: Int): AllEpisodes =
        episodesEndPoint.retrieveAllEpisodes(page).toAllEpisodes()
}