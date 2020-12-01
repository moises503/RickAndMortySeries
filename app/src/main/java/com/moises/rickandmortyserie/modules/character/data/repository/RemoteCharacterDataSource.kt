package com.moises.rickandmortyserie.modules.character.data.repository

import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters


interface RemoteCharacterDataSource {
    suspend fun retrieveAllCharacters(currentPage : Int) : AllCharacters
}