package com.moises.rickandmortyserie.modules.character.domain.model

import com.moises.rickandmortyserie.core.arch.model.Pagination

data class AllCharacters(val pagination: Pagination, val all : List<Character>)