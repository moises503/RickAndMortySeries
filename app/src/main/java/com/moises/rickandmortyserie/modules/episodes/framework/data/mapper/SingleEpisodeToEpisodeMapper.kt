package com.moises.rickandmortyserie.modules.episodes.framework.data.mapper

import com.moises.rickandmortyserie.core.arch.Mapper
import com.moises.rickandmortyserie.modules.episodes.domain.model.Episode
import com.moises.rickandmortyserie.modules.episodes.framework.data.model.SingleEpisode

class SingleEpisodeToEpisodeMapper : Mapper<SingleEpisode, Episode>() {
    override fun transform(value: SingleEpisode): Episode {
        return Episode(
                id = value.id ?: 0,
                name = value.name.orEmpty(),
                airDate = value.airDate.orEmpty()
        )
    }
}