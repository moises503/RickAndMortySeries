package com.moises.rickandmortyserie.modules.character.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.core.ui.*
import com.moises.rickandmortyserie.databinding.FragmentCharactersBinding
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.framework.presentation.AllCharactersScreenState
import com.moises.rickandmortyserie.modules.character.framework.presentation.CharacterViewModel
import com.moises.rickandmortyserie.modules.character.framework.ui.adapter.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CharacterFragment : BaseFragment<ScreenState<AllCharactersScreenState>>() {

    private val characterViewModel : CharacterViewModel by viewModels()
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var charactersAdapter: CharactersAdapter

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

    override fun bindViews(): Unit = with(binding) {
        lstCharacters.apply {
            charactersAdapter = CharactersAdapter {
                activity?.toast(it.name)
            }
            addItemDecoration(SpacesItemDecoration(SPACE_ITEM_DECORATION))
            adapter = charactersAdapter
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL)
            addScrollListener()
        }
    }

    override fun bindFragmentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.pbCharactersLoad.visible()
    }

    override fun hideLoader() {
        binding.pbCharactersLoad.gone()
    }

    private fun renderCharacters(allCharactersScreenState: AllCharactersScreenState) {
        hideLoader()
        when(allCharactersScreenState) {
            is AllCharactersScreenState.Success -> populateCharactersList(allCharactersScreenState.allCharacters)
            is AllCharactersScreenState.Error -> showError(allCharactersScreenState.message)
        }
    }

    private fun populateCharactersList(all : List<Character>) {
        charactersAdapter.updateDataSet(all.toMutableList())
    }

    private fun addScrollListener() {
        InfiniteScrollProvider().attach(
            binding.lstCharacters,
            object : InfiniteScrollProvider.OnLoadMoreListener {
                override fun onLoadMore() {
                    characterViewModel.retrieveAllCharacters()
                }
            })
    }

    companion object {
        private const val SPACE_ITEM_DECORATION = 12
    }
}