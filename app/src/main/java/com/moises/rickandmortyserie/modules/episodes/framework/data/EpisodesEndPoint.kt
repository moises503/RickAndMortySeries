package com.moises.rickandmortyserie.modules.episodes.framework.data

import com.moises.rickandmortyserie.modules.episodes.framework.data.model.AllEpisodesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodesEndPoint {
    @GET("episode/")
    suspend fun retrieveAllEpisodes(@Query("page") page : Int) : AllEpisodesResponse
}