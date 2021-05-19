package com.moises.rickandmortyserie.modules.character.framework.util

import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.framework.data.mapper.AllCharactersResponseToAllCharactersMapper
import com.moises.rickandmortyserie.modules.character.framework.data.mapper.CharacterResponseToCharacterMapper
import com.moises.rickandmortyserie.modules.character.framework.data.mapper.InfoToPaginationMapper
import com.moises.rickandmortyserie.modules.character.framework.data.mapper.SingleCharacterToCharacterMapper
import com.moises.rickandmortyserie.modules.character.framework.data.model.AllCharactersResponse
import com.moises.rickandmortyserie.modules.character.framework.data.model.CharacterResponse

fun AllCharactersResponse.toAllCharacters(): AllCharacters {
    return AllCharactersResponseToAllCharactersMapper(
        InfoToPaginationMapper(),
        SingleCharacterToCharacterMapper()
    ).transform(this)
}

fun CharacterResponse.toCharacter(): Character {
    return CharacterResponseToCharacterMapper().transform(this)
}