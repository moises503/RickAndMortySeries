package com.moises.rickandmortyserie.modules.character.domain.usecase

import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.arch.SimpleUseCase
import com.moises.rickandmortyserie.modules.character.domain.repository.CharacterRepository
import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

class AllCharactersUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val charactersRepository: CharacterRepository
) : SimpleUseCase<AllCharactersUseCase.Params, Flow<AllCharacters>>() {

    override fun execute(params: Params?): Flow<AllCharacters> {
        params?.let {
            return charactersRepository.retrieveAllCharacters(it.currentPage)
                .flowOn(dispatcherProvider.ioDispatcher())
        } ?: throw Exception("Params can not be null")
    }

    data class Params(val currentPage: Int)
}