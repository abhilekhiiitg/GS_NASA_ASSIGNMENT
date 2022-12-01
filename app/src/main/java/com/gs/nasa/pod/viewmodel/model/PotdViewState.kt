package com.gs.nasa.pod.viewmodel.model

sealed class PotdViewState {
    data class Success<T>(val data: T) : PotdViewState()
    object Loading : PotdViewState()
    data class Error(val error: String? = null) : PotdViewState()
}