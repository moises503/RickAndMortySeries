package com.moises.rickandmortyserie.modules.episodes.domain.usecase

import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.arch.SimpleUseCase
import com.moises.rickandmortyserie.core.assets.ResourceManager
import com.moises.rickandmortyserie.core.di.Constants
import com.moises.rickandmortyserie.modules.episodes.domain.model.AllEpisodes
import com.moises.rickandmortyserie.modules.episodes.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AllEpisodesUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val episodesRepository: EpisodesRepository,
    private val resourceManager: ResourceManager
) : SimpleUseCase<AllEpisodesUseCase.Params, Flow<AllEpisodes>>() {

    override fun execute(params: Params?): Flow<AllEpisodes> {
        params?.let {
            return episodesRepository.retrieveAllEpisodes(it.page).flowOn(dispatcherProvider.ioDispatcher())
        } ?: throw Exception(resourceManager.providesStringMessage(identifier = Constants.NULL_PARAMETERS_ERROR))
    }

    data class Params(val page: Int)
}