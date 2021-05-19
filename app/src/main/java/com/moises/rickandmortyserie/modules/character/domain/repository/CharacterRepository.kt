package com.moises.rickandmortyserie.modules.character.domain.repository

import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun retrieveAllCharacters(currentPage : Int) : Flow<AllCharacters>
    fun retrieveSingleCharacter(characterId: String) : Flow<Character>
}