package com.moises.rickandmortyserie.modules.episodes.framework.presentation

import com.moises.rickandmortyserie.modules.episodes.domain.model.Episode

sealed class EpisodesScreenState {
    data class Success(val allEpisodes : List<Episode>) : EpisodesScreenState()
    data class Error(val message : String) : EpisodesScreenState()
}