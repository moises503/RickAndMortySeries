package com.moises.rickandmortyserie.modules.character.framework.presentation

import com.moises.rickandmortyserie.modules.character.domain.model.Character

sealed class AllCharactersScreenState {
    data class Error(val message : String) : AllCharactersScreenState()
    data class Success(val allCharacters : List<Character>) : AllCharactersScreenState()
}