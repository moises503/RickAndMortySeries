package com.moises.rickandmortyserie.modules.episodes.framework.data.mapper

import com.moises.rickandmortyserie.core.arch.Mapper
import com.moises.rickandmortyserie.modules.episodes.domain.model.AllEpisodes
import com.moises.rickandmortyserie.modules.episodes.framework.data.model.AllEpisodesResponse

class AllEpisodesResponseToAllEpisodesMapper(
    private val infoToPaginationMapper: InfoToPaginationMapper,
    private val singleEpisodeToEpisodeMapper: SingleEpisodeToEpisodeMapper
) : Mapper<AllEpisodesResponse, AllEpisodes>() {
    override fun transform(value: AllEpisodesResponse): AllEpisodes {
        return AllEpisodes(
            pagination = infoToPaginationMapper.transform(value.info),
            all = singleEpisodeToEpisodeMapper.transformCollection(value.results.orEmpty())
        )
    }
}