package com.moises.rickandmortyserie.modules.episodes.framework.util

import com.moises.rickandmortyserie.modules.episodes.domain.model.AllEpisodes
import com.moises.rickandmortyserie.modules.episodes.framework.data.model.AllEpisodesResponse
import com.moises.rickandmortyserie.modules.episodes.framework.data.mapper.AllEpisodesResponseToAllEpisodesMapper
import com.moises.rickandmortyserie.modules.episodes.framework.data.mapper.InfoToPaginationMapper
import com.moises.rickandmortyserie.modules.episodes.framework.data.mapper.SingleEpisodeToEpisodeMapper

fun AllEpisodesResponse.toAllEpisodes(): AllEpisodes {
    return AllEpisodesResponseToAllEpisodesMapper(
        InfoToPaginationMapper(),
        SingleEpisodeToEpisodeMapper()
    ).transform(this)
}