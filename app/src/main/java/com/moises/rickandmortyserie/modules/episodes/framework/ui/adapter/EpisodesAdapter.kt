package com.moises.rickandmortyserie.modules.episodes.framework.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moises.rickandmortyserie.R
import com.moises.rickandmortyserie.core.ui.inflate
import com.moises.rickandmortyserie.databinding.EpisodeViewItemBinding
import com.moises.rickandmortyserie.modules.episodes.domain.model.Episode

class EpisodesAdapter(
        private var episodes : MutableList<Episode> = mutableListOf()
) : RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder =
            EpisodesViewHolder(parent.inflate(R.layout.episode_view_item))

    override fun getItemCount(): Int = episodes.size

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    fun updateDataSet(list: MutableList<Episode>) {
        episodes = list
        notifyDataSetChanged()
    }

    inner class EpisodesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding =  EpisodeViewItemBinding.bind(view)

        fun bind(episode: Episode) {
            binding.nameTextView.text = episode.name
            binding.airDateTextView.text = episode.airDate
        }
    }
}