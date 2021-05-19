package com.moises.rickandmortyserie.modules.character.framework.presentation

import com.moises.rickandmortyserie.modules.character.domain.model.Character

sealed class SingleCharacterViewState {
    data class Error(val message: String): SingleCharacterViewState()
    data class Success(val character: Character): SingleCharacterViewState()
}