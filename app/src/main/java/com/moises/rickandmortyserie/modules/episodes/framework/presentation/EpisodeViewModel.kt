package com.moises.rickandmortyserie.modules.episodes.framework.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.core.assets.ResourceManager
import com.moises.rickandmortyserie.modules.episodes.domain.model.Episode
import com.moises.rickandmortyserie.modules.episodes.domain.usecase.AllEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class EpisodeViewModel @Inject constructor(
        private val allEpisodesUseCase: AllEpisodesUseCase,
        private val resourceManager: ResourceManager
) : ViewModel() {
    private var _allEpisodesScreenState = MutableLiveData<ScreenState<EpisodesScreenState>>()
    private var currentPage: Int = 1
    private var maxPages: Int = 2
    private var allEpisodes : MutableList<Episode> = mutableListOf()
    val episodesScreenState: LiveData<ScreenState<EpisodesScreenState>>
        get() = _allEpisodesScreenState

    fun retrieveAllEpisodes() {
        when {
            canLoadMoreEpisodes() -> sendEpisodesRequest()
            else -> {
                _allEpisodesScreenState.postValue(
                        ScreenState.Render(
                                EpisodesScreenState.Error(
                                        resourceManager.providesStringMessage(identifier = CAN_NOT_LOAD_MORE_EPISODES)
                                )
                        )
                )
            }
        }
    }

    private fun canLoadMoreEpisodes(): Boolean =
            currentPage < maxPages

    private fun sendEpisodesRequest() {
        viewModelScope.launch {
            allEpisodesUseCase.execute(AllEpisodesUseCase.Params(currentPage))
                    .onStart {
                        _allEpisodesScreenState.postValue(ScreenState.Loading)
                    }.catch {
                        _allEpisodesScreenState.postValue(
                                ScreenState.Render(
                                        EpisodesScreenState.Error(
                                                message = resourceManager.providesStringMessage(identifier = EPISODES_ERROR)
                                        )
                                )
                        )
                    }.collect {
                        maxPages = it.pagination.pages
                        currentPage++
                        allEpisodes.addAll(it.all)
                        _allEpisodesScreenState.postValue(
                                ScreenState.Render(
                                        EpisodesScreenState.Success(
                                                allEpisodes = allEpisodes
                                        )
                                )
                        )
                    }
        }
    }

    companion object {
        const val CAN_NOT_LOAD_MORE_EPISODES = "error_no_more_episodes"
        const val EPISODES_ERROR = "error_episodes"
    }
}