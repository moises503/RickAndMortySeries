package com.moises.rickandmortyserie.modules.character.framework.data.mapper

import com.moises.rickandmortyserie.core.arch.Mapper
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.framework.data.model.SingleCharacter

class SingleCharacterToCharacterMapper : Mapper<SingleCharacter, Character>(){
    override fun transform(value: SingleCharacter): Character {
        return Character(
            image = value.image.orEmpty(),
            name = value.name.orEmpty(),
            status = value.status.orEmpty(),
            species = value.species.orEmpty(),
            type = value.type.orEmpty(),
            gender = value.gender.orEmpty()
        )
    }
}