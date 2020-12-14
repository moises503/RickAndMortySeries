package com.moises.rickandmortyserie.modules.character.framework.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.modules.character.domain.usecase.AllCharactersUseCase
import com.moises.rickandmortyserie.modules.character.framework.res.StringResources
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterViewModel @ViewModelInject constructor(
    private val allCharactersUseCase: AllCharactersUseCase,
    private val stringResources: StringResources
) : ViewModel() {

    private var _allCharactersScreenState = MutableLiveData<ScreenState<AllCharactersScreenState>>()
    private var maxPages: Int = 2
    private var currentPage: Int = 1

    val allCharactersScreenState: LiveData<ScreenState<AllCharactersScreenState>>
        get() = _allCharactersScreenState

    fun retrieveAllCharacters() {
        when {
            canLoadMorePages() -> sendCharacterRequest()
            else -> _allCharactersScreenState.postValue(
                ScreenState.Render(
                    AllCharactersScreenState.Error(
                        stringResources.getCanNotLoadErrorMessage()
                    )
                )
            )
        }
    }

    private fun canLoadMorePages() = currentPage < maxPages

    @ExperimentalCoroutinesApi
    private fun sendCharacterRequest() {
        viewModelScope.launch {
            allCharactersUseCase.execute(AllCharactersUseCase.Params(currentPage))
                .onStart {
                    _allCharactersScreenState.postValue(ScreenState.Loading)
                }.catch {
                    _allCharactersScreenState.postValue(
                        ScreenState.Render(
                            AllCharactersScreenState.Error(
                                stringResources.getCharactersErrorMessage()
                            )
                        )
                    )
                }.collect {
                    maxPages = it.pagination.pages
                    currentPage++
                    _allCharactersScreenState.postValue(
                        ScreenState.Render(
                            AllCharactersScreenState.Success(
                                it.all
                            )
                        )
                    )
                }
        }
    }
}