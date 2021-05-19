package com.moises.rickandmortyserie.modules.character.framework.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.modules.character.domain.usecase.SingleCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleCharacterViewModel @Inject constructor(
    private val singleCharacterUseCase: SingleCharacterUseCase
) : ViewModel() {
    private var _characterState = MutableLiveData<ScreenState<SingleCharacterViewState>>()
    val characterState : LiveData<ScreenState<SingleCharacterViewState>> get() = _characterState

    fun retrieveCharacter(characterId: Int) {
        _characterState.postValue(ScreenState.Loading)
        viewModelScope.launch {
            singleCharacterUseCase.execute(characterId).catch {
                _characterState.postValue(ScreenState.Render(SingleCharacterViewState.Error(it.localizedMessage ?: "")))
            }.collect {
                _characterState.postValue(ScreenState.Render(SingleCharacterViewState.Success(it)))
            }
        }
    }
}