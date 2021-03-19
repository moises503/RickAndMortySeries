package com.moises.rickandmortyserie.modules.episodes.domain.model

import com.moises.rickandmortyserie.core.arch.model.Pagination

data class AllEpisodes(val pagination: Pagination, val all : List<Episode>)