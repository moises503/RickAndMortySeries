package com.moises.rickandmortyserie.modules.episodes.framework.res

import android.content.Context
import com.moises.rickandmortyserie.R

class StringResourcesImpl(private val context: Context) : StringResources {

    override fun getErrorWhenCanNotLoadMoreEpisodes(): String =
        context.getString(R.string.error_no_more_episodes)

    override fun getErrorEpisodes(): String =
        context.getString(R.string.error_episodes)

}