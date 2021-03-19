package com.moises.rickandmortyserie.modules.character.framework.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.core.assets.ResourceManager
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.domain.usecase.AllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
        private val allCharactersUseCase: AllCharactersUseCase,
        private val resourceManager: ResourceManager
) : ViewModel() {

    private var _allCharactersScreenState = MutableLiveData<ScreenState<AllCharactersScreenState>>()
    private var maxPages: Int = 2
    private var currentPage: Int = 1
    private var allCharacters = mutableListOf<Character>()

    val allCharactersScreenState: LiveData<ScreenState<AllCharactersScreenState>>
        get() = _allCharactersScreenState

    @ExperimentalCoroutinesApi
    fun retrieveAllCharacters() {
        when {
            canLoadMorePages() -> sendCharacterRequest()
            else -> _allCharactersScreenState.postValue(
                    ScreenState.Render(
                            AllCharactersScreenState.Error(
                                    resourceManager.providesStringMessage(identifier = NOT_LOAD_ERROR)
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
                                                resourceManager.providesStringMessage(identifier = CHARACTERS_ERROR)
                                        )
                                )
                        )
                    }.collect {
                        maxPages = it.pagination.pages
                        currentPage++
                        allCharacters.addAll(it.all)
                        _allCharactersScreenState.postValue(
                                ScreenState.Render(
                                        AllCharactersScreenState.Success(
                                                allCharacters
                                        )
                                )
                        )
                    }
        }
    }

    companion object {
        private const val NOT_LOAD_ERROR = "can_not_load_more_characters"
        private const val CHARACTERS_ERROR = "characters_error"
    }
}