package com.moises.rickandmortyserie.modules.character.domain.usecase

import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.arch.SimpleUseCase
import com.moises.rickandmortyserie.core.assets.ResourceManager
import com.moises.rickandmortyserie.core.di.Constants.NULL_PARAMETERS_ERROR
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SingleCharacterUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val charactersRepository: CharacterRepository,
    private val resourceManager: ResourceManager
) : SimpleUseCase<String, Flow<Character>>() {

    override fun execute(params: String?): Flow<Character> {
        params?.let {
            return charactersRepository.retrieveSingleCharacter(it)
                .flowOn(dispatcherProvider.ioDispatcher())
        }  ?: throw IllegalArgumentException(resourceManager.providesStringMessage(identifier = NULL_PARAMETERS_ERROR))
    }
}