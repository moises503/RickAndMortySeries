package com.moises.rickandmortyserie.core.assets

interface ResourceManager {
    fun providesStringMessage(identifier: String): String
    fun providesStringResource(identifier: String): Int?
    fun providesDrawableResource(identifier: String): Int?
}