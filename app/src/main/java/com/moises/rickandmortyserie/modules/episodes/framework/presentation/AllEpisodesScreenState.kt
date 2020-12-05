package com.moises.rickandmortyserie.modules.episodes.framework.presentation

import com.moises.rickandmortyserie.modules.episodes.domain.model.Episode

sealed class AllEpisodesScreenState {
    data class Success(val allEpisodes : List<Episode>) : AllEpisodesScreenState()
    data class Error(val message : String) : AllEpisodesScreenState()
}