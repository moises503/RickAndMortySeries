package com.moises.rickandmortyserie.modules.episodes.data.datasource

import com.moises.rickandmortyserie.modules.episodes.domain.model.AllEpisodes

interface RemoteEpisodesDataSource {
    suspend fun retrieveAllEpisodes(page : Int) : AllEpisodes
}