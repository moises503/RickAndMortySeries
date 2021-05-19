package com.moises.rickandmortyserie.modules.character.framework.data.model

import com.google.gson.annotations.SerializedName

data class Location(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)