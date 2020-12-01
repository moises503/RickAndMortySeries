package com.moises.rickandmortyserie.modules.character.framework.data.mapper

import com.moises.rickandmortyserie.core.arch.Mapper
import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.character.framework.data.model.AllCharactersResponse

class AllCharactersResponseToAllCharactersMapper(
    private val infoToPaginationMapper: InfoToPaginationMapper,
    private val singleCharacterToCharacterMapper: SingleCharacterToCharacterMapper
) : Mapper<AllCharactersResponse, AllCharacters>() {
    override fun transform(value: AllCharactersResponse): AllCharacters {
        return AllCharacters(
            pagination = infoToPaginationMapper.transform(value.info),
            all = singleCharacterToCharacterMapper.transformCollection(value.results)
        )
    }
}