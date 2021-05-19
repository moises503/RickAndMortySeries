package com.moises.rickandmortyserie.modules.character.data.datasource

import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.character.domain.model.Character


interface RemoteCharacterDataSource {
    suspend fun retrieveAllCharacters(currentPage : Int) : AllCharacters
    suspend fun retrieveSingleCharacter(characterId : String) : Character
}