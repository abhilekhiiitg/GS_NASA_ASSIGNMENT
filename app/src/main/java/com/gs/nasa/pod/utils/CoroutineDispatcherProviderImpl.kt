package com.gs.nasa.pod.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import javax.inject.Inject

class CoroutineDispatcherProviderImpl @Inject constructor(): CoroutineDispatcherProvider {
    override fun main() = Dispatchers.Main

    override fun io() = Dispatchers.IO

    override fun undefined() = Dispatchers.Unconfined

    override fun default() = Dispatchers.Default

    @OptIn(DelicateCoroutinesApi::class)
    override fun singleThread(name: String): CoroutineDispatcher = newSingleThreadContext(name)
}