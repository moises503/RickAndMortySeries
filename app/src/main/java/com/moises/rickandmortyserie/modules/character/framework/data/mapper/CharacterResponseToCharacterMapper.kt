package com.moises.rickandmortyserie.modules.character.framework.data.mapper

import com.moises.rickandmortyserie.core.arch.Mapper
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.framework.data.model.CharacterResponse

class CharacterResponseToCharacterMapper : Mapper<CharacterResponse, Character>() {
    override fun transform(value: CharacterResponse): Character {
        return Character(
            id = value.id.toString(),
            image = value.image.orEmpty(),
            name = value.name.orEmpty(),
            status = value.status.orEmpty(),
            species = value.species.orEmpty(),
            type = value.type.orEmpty(),
            gender = value.gender.orEmpty()
        )
    }
}