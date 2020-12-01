package com.moises.rickandmortyserie.modules.character.framework.data

import com.moises.rickandmortyserie.modules.character.framework.data.model.AllCharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersEndPoint {
    @GET("character/")
    suspend fun getAllCharacters(@Query("page") page : Int = 1) : AllCharactersResponse
}