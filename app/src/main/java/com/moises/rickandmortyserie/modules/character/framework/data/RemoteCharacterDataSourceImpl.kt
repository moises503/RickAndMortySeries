package com.moises.rickandmortyserie.modules.character.framework.data

import com.moises.rickandmortyserie.modules.character.data.repository.RemoteCharacterDataSource
import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.framework.util.toAllCharacters
import javax.inject.Inject

class RemoteCharacterDataSourceImpl @Inject constructor(private val charactersEndPoint: CharactersEndPoint) :
    RemoteCharacterDataSource {
    override suspend fun retrieveAllCharacters(currentPage: Int): AllCharacters {
        return charactersEndPoint.getAllCharacters(currentPage).toAllCharacters()
    }

    override suspend fun retrieveSingleCharacter(characterId: Int): Character {
        TODO("Not yet implemented")
    }
}