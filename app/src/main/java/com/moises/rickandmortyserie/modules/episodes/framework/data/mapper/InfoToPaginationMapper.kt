package com.moises.rickandmortyserie.modules.episodes.framework.data.mapper

import com.moises.rickandmortyserie.core.arch.Mapper
import com.moises.rickandmortyserie.core.arch.model.Pagination
import com.moises.rickandmortyserie.modules.episodes.framework.data.model.Info

class InfoToPaginationMapper : Mapper<Info, Pagination>() {
    override fun transform(value: Info): Pagination {
        return Pagination(pages = value.pages ?: 0)
    }
}