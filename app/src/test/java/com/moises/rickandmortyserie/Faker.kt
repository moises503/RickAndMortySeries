package com.moises.rickandmortyserie

import com.moises.rickandmortyserie.core.arch.model.Pagination
import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters

object Faker {
    fun makeMockCharacterSuccessResponse(): AllCharacters {
        return AllCharacters(
                pagination = Pagination(pages = 10),
                all = listOf(
                        CharacterTest(
                                image = "",
                                name = "Rick Sanchez",
                                status = "Alive",
                                species = "Human",
                                type = "",
                                gender = "Male"
                        ),
                        CharacterTest(
                                image = "",
                                name = "Morty Smith",
                                status = "Alive",
                                species = "Human",
                                type = "",
                                gender = "Male"
                        )
                )
        )
    }
}