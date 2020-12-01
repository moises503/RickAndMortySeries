package com.moises.rickandmortyserie.modules.character.framework.res

import android.content.Context
import com.moises.rickandmortyserie.R

class StringResourcesImpl(private val context: Context) : StringResources {
    override fun getCharactersErrorMessage(): String = context.getString(R.string.characters_error)

    override fun getCanNotLoadErrorMessage(): String = context.getString(R.string.can_not_load_more_characters)
}