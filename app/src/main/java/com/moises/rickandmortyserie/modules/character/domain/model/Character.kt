package com.moises.rickandmortyserie.modules.character.domain.model

data class Character(val id: String,
                     val name: String,
                     val image : String,
                     val status: String,
                     val species: String,
                     val type: String,
                     val gender: String)