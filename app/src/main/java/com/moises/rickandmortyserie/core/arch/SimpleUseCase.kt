package com.moises.rickandmortyserie.core.arch

abstract class SimpleUseCase<Params, Result> {
    abstract fun execute(params : Params?) : Result
}