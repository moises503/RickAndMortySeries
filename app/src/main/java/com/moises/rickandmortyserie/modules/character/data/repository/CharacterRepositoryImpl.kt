package com.moises.rickandmortyserie.modules.character.data.repository

import com.moises.rickandmortyserie.modules.character.data.datasource.RemoteCharacterDataSource
import com.moises.rickandmortyserie.modules.character.domain.repository.CharacterRepository
import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteCharacterDataSource: RemoteCharacterDataSource
) : CharacterRepository {
    override fun retrieveAllCharacters(currentPage: Int): Flow<AllCharacters> = flow {
        emit(remoteCharacterDataSource.retrieveAllCharacters(currentPage))
    }

    override fun retrieveSingleCharacter(characterId: String): Flow<Character> = flow {
        emit(remoteCharacterDataSource.retrieveSingleCharacter(characterId))
    }
}