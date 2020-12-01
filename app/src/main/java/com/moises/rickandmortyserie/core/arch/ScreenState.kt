package com.moises.rickandmortyserie.core.arch

sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    class Render<T>(val data: T) : ScreenState<T>()
}