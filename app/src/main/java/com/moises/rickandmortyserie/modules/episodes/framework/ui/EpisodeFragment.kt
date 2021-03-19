package com.moises.rickandmortyserie.modules.episodes.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.core.ui.BaseFragment
import com.moises.rickandmortyserie.core.ui.gone
import com.moises.rickandmortyserie.core.ui.toast
import com.moises.rickandmortyserie.core.ui.visible
import com.moises.rickandmortyserie.databinding.FragmentEpisodesBinding
import com.moises.rickandmortyserie.modules.episodes.domain.model.Episode
import com.moises.rickandmortyserie.modules.episodes.framework.presentation.EpisodeViewModel
import com.moises.rickandmortyserie.modules.episodes.framework.presentation.EpisodesScreenState
import com.moises.rickandmortyserie.modules.episodes.framework.ui.adapter.EpisodesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class EpisodeFragment : BaseFragment<ScreenState<EpisodesScreenState>>() {

    private val episodesViewModel: EpisodeViewModel by viewModels()
    private val episodesAdapter = EpisodesAdapter()
    private lateinit var binding: FragmentEpisodesBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        attachObservers()
    }

    override fun bindFragmentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        episodesViewModel.retrieveAllEpisodes()
    }

    override fun bindViews() {
        binding.episodesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = episodesAdapter
        }
    }

    override fun attachObservers() {
        episodesViewModel.episodesScreenState.observe(viewLifecycleOwner, Observer {
            renderScreenState(it)
        })
    }

    override fun hideLoader() {
        binding.episodesProgressBar.gone()
    }

    override fun showLoader() {
        binding.episodesProgressBar.visible()
    }

    override fun showError(message: String) {
        requireContext().toast(message)
    }

    override fun renderScreenState(screenState: ScreenState<EpisodesScreenState>) {
        when (screenState) {
            is ScreenState.Loading -> showLoader()
            is ScreenState.Render -> renderEpisodesInfo(screenState.data)
        }
    }

    private fun renderEpisodesInfo(episodesViewState: EpisodesScreenState) {
        hideLoader()
        when (episodesViewState) {
            is EpisodesScreenState.Error -> showError(episodesViewState.message)
            is EpisodesScreenState.Success -> showEpisodes(episodesViewState.allEpisodes)
        }
    }

    private fun showEpisodes(allEpisodes: List<Episode>) {
        episodesAdapter.updateDataSet(allEpisodes.toMutableList())
    }
}