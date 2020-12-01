package com.moises.rickandmortyserie.core.arch

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl : DispatcherProvider {

    override fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    override fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    override fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}