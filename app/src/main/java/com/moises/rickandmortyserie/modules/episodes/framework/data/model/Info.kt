package com.moises.rickandmortyserie.modules.episodes.framework.data.model

import com.google.gson.annotations.SerializedName

data class Info(

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("pages")
    val pages: Int? = null,

    @field:SerializedName("prev")
    val prev: Any? = null,

    @field:SerializedName("count")
    val count: Int? = null
)