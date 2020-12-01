package com.moises.rickandmortyserie.modules.character.framework.util

import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.character.framework.data.mapper.AllCharactersResponseToAllCharactersMapper
import com.moises.rickandmortyserie.modules.character.framework.data.mapper.InfoToPaginationMapper
import com.moises.rickandmortyserie.modules.character.framework.data.mapper.SingleCharacterToCharacterMapper
import com.moises.rickandmortyserie.modules.character.framework.data.model.AllCharactersResponse

fun AllCharactersResponse.toAllCharacters(): AllCharacters {
    return AllCharactersResponseToAllCharactersMapper(
        InfoToPaginationMapper(),
        SingleCharacterToCharacterMapper()
    ).transform(this)
}