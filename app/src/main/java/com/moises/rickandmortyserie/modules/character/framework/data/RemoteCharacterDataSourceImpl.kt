package com.moises.rickandmortyserie.modules.character.framework.data

import com.moises.rickandmortyserie.modules.character.data.datasource.RemoteCharacterDataSource
import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.framework.util.toAllCharacters
import com.moises.rickandmortyserie.modules.character.framework.util.toCharacter
import javax.inject.Inject

class RemoteCharacterDataSourceImpl @Inject constructor(
    private val charactersEndPoint: CharactersEndPoint
) : RemoteCharacterDataSource {

    override suspend fun retrieveAllCharacters(currentPage: Int): AllCharacters {
        return charactersEndPoint.getAllCharacters(currentPage).toAllCharacters()
    }

    override suspend fun retrieveSingleCharacter(characterId: Int): Character {
        return charactersEndPoint.retrieveCharacter(characterId.toString()).toCharacter()
    }
}