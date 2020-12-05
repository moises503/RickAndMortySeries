package com.moises.rickandmortyserie.modules.episodes.framework.data.model

import com.google.gson.annotations.SerializedName

data class AllEpisodesResponse(

	@field:SerializedName("results")
	val results: List<SingleEpisode>? = null,

	@field:SerializedName("info")
	val info: Info
)
