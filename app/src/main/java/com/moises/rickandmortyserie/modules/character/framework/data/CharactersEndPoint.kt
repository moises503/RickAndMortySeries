package com.moises.rickandmortyserie.modules.character.framework.data

import com.moises.rickandmortyserie.modules.character.framework.data.model.AllCharactersResponse
import com.moises.rickandmortyserie.modules.character.framework.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersEndPoint {
    @GET("character/")
    suspend fun getAllCharacters(@Query("page") page : Int = 1) : AllCharactersResponse

    @GET("character/{characterId}")
    suspend fun retrieveCharacter(@Path("characterId") characterId : String) : CharacterResponse
}