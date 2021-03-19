package com.moises.rickandmortyserie

import com.moises.rickandmortyserie.core.arch.model.Pagination
import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.episodes.domain.model.AllEpisodes
import com.moises.rickandmortyserie.modules.episodes.domain.model.Episode

typealias CharacterTest = com.moises.rickandmortyserie.modules.character.domain.model.Character

object Faker {
    fun makeMockCharacterSuccessResponse(pages: Int = 2, characters: Int = 10): AllCharacters {
        return AllCharacters(
                pagination = Pagination(pages = pages),
                all = providesCharacters(characters)
        )
    }

    fun providesCharacters(range: Int): List<CharacterTest> {
        val characters = mutableListOf<CharacterTest>()
        for (element in 1..range) {
            characters.add(
                    CharacterTest(
                            image = "",
                            name = "Rick & Morty $element",
                            status = "Alive",
                            species = "Human",
                            type = "",
                            gender = "Male"
                    )
            )
        }
        return characters
    }

    fun providesEpisodesSuccessResponse(pages: Int = 1, range: Int = 10): AllEpisodes {
        return AllEpisodes(
                pagination = Pagination(pages),
                all = providesEpisodes(10)
        )
    }

    fun providesEpisodes(range: Int): List<Episode> {
        val episodes = mutableListOf<Episode>()
        for (element in 1..range) {
            episodes.add(Episode(
                    id = element,
                    name = "Episode $element",
                    airDate = "December $element"
            ))
        }
        return episodes
    }
}