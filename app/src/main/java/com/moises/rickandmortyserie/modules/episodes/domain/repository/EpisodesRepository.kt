package com.moises.rickandmortyserie.modules.episodes.domain.repository

import com.moises.rickandmortyserie.modules.episodes.domain.model.AllEpisodes
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {
    fun retrieveAllEpisodes(page : Int) : Flow<AllEpisodes>
}