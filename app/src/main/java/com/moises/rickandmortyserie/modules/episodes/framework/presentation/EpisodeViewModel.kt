package com.moises.rickandmortyserie.modules.episodes.framework.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.modules.episodes.domain.usecase.AllEpisodesUseCase
import com.moises.rickandmortyserie.modules.episodes.framework.res.StringResources
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class EpisodeViewModel @ViewModelInject constructor(
    private val allEpisodesUseCase: AllEpisodesUseCase,
    private val stringResources: StringResources
) : ViewModel() {
    private var _allEpisodesScreenState = MutableLiveData<ScreenState<AllEpisodesScreenState>>()
    private var currentPage: Int = 1
    private var maxPages: Int = 2
    val allEpisodesScreenState: LiveData<ScreenState<AllEpisodesScreenState>>
        get() = _allEpisodesScreenState

    fun retrieveAllEpisodes() {
        when {
            canLoadMoreEpisodes() -> sendEpisodesRequest()
            else -> {
                _allEpisodesScreenState.postValue(
                    ScreenState.Render(
                        AllEpisodesScreenState.Error(
                            stringResources.getErrorWhenCanNotLoadMoreEpisodes()
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
                            AllEpisodesScreenState.Error(
                                message = stringResources.getErrorEpisodes()
                            )
                        )
                    )
                }.collect {
                    _allEpisodesScreenState.postValue(
                        ScreenState.Render(
                            AllEpisodesScreenState.Success(
                                allEpisodes = it.all
                            )
                        )
                    )
                }
        }
    }
}