package com.gs.nasa.pod.utils

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun undefined(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun singleThread(name: String): CoroutineDispatcher
}