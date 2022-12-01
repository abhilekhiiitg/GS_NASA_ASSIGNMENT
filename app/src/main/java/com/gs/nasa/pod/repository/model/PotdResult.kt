package com.gs.nasa.pod.repository.model

sealed class PotdResult {
    data class Success<T>(val data: T) : PotdResult()
    data class Empty(val message: String? = null) : PotdResult()
    data class Error(val error: String? = null) : PotdResult()
}