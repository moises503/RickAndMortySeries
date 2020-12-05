package com.moises.rickandmortyserie.modules.episodes.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moises.rickandmortyserie.R
import com.moises.rickandmortyserie.databinding.FragmentEpisodesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment : Fragment() {
    private lateinit var fragmentEpisodesBinding: FragmentEpisodesBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_episodes, container, false)
        return root
    }
}