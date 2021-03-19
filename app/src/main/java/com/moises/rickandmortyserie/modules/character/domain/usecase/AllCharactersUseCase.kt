package com.moises.rickandmortyserie.modules.character.domain.usecase

import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.arch.SimpleUseCase
import com.moises.rickandmortyserie.core.assets.AppResourceManager
import com.moises.rickandmortyserie.core.assets.ResourceManager
import com.moises.rickandmortyserie.core.di.Constants.NULL_PARAMETERS_ERROR
import com.moises.rickandmortyserie.modules.character.domain.repository.CharacterRepository
import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AllCharactersUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val charactersRepository: CharacterRepository,
    private val resourceManager: ResourceManager
) : SimpleUseCase<AllCharactersUseCase.Params, Flow<AllCharacters>>() {

    override fun execute(params: Params?): Flow<AllCharacters> {
        params?.let {
            return charactersRepository.retrieveAllCharacters(it.currentPage)
                .flowOn(dispatcherProvider.ioDispatcher())
        } ?: throw Exception(resourceManager.providesStringMessage(identifier = NULL_PARAMETERS_ERROR))
    }

    data class Params(val currentPage: Int)
}