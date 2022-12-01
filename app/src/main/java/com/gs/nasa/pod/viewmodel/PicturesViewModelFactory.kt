package com.gs.nasa.pod.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gs.nasa.pod.repository.PicturesRepository
import com.gs.nasa.pod.utils.CoroutineDispatcherProvider
import javax.inject.Inject

class PicturesViewModelFactory @Inject constructor(
    private val repository: PicturesRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PicturesViewModel(repository, coroutineDispatcherProvider) as T
    }
}