package com.moises.rickandmortyserie.modules.character.framework.data.model

import com.google.gson.annotations.SerializedName

data class AllCharactersResponse(

	@field:SerializedName("results")
	val results: List<SingleCharacter>,

	@field:SerializedName("info")
	val info: Info
)

