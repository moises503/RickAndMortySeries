package com.moises.rickandmortyserie.modules.character.framework.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.core.ui.BaseFragment
import com.moises.rickandmortyserie.core.ui.toast
import com.moises.rickandmortyserie.databinding.FragmentCharactersBinding
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.framework.presentation.AllCharactersScreenState
import com.moises.rickandmortyserie.modules.character.framework.presentation.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : BaseFragment<ScreenState<AllCharactersScreenState>>() {

    private val characterViewModel : CharacterViewModel by viewModels()
    private lateinit var fragmentCharactersBinding: FragmentCharactersBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        attachObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        characterViewModel.retrieveAllCharacters()
    }

    override fun attachObservers() {
        characterViewModel.allCharactersScreenState.observe(viewLifecycleOwner, Observer {
            renderScreenState(it)
        })
    }

    override fun bindViews() {

    }

    override fun bindFragmentView(inflater: LayoutInflater, container: ViewGroup?): View {
        fragmentCharactersBinding = FragmentCharactersBinding.inflate(inflater, container, false)
        return fragmentCharactersBinding.root
    }

    override fun renderScreenState(screenState: ScreenState<AllCharactersScreenState>) {
        when(screenState) {
            is ScreenState.Loading -> showLoader()
            is ScreenState.Render -> renderCharacters(screenState.data)
        }
    }

    override fun showError(message: String) {
        requireContext().toast(message)
    }

    override fun showLoader() {

    }

    override fun hideLoader() {

    }

    private fun renderCharacters(allCharactersScreenState: AllCharactersScreenState) {
        hideLoader()
        when(allCharactersScreenState) {
            is AllCharactersScreenState.Success -> populateCharactersList(allCharactersScreenState.allCharacters)
            is AllCharactersScreenState.Error -> showError(allCharactersScreenState.message)
        }
    }

    private fun populateCharactersList(all : List<Character>) {
        Log.e("CHARACTERS", all.toString())
    }
}