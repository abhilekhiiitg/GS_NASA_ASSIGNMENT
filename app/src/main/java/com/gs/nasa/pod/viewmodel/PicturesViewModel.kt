package com.gs.nasa.pod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gs.nasa.pod.repository.PicturesRepository
import com.gs.nasa.pod.repository.model.PotdResult
import com.gs.nasa.pod.utils.CoroutineDispatcherProvider
import com.gs.nasa.pod.viewmodel.model.PotdViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

class PicturesViewModel @Inject constructor(
    private val repository: PicturesRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private var _viewStateLiveData = MutableLiveData<PotdViewState>(PotdViewState.Loading)
    val viewStateLiveData: LiveData<PotdViewState> get() = _viewStateLiveData


    fun updateFavouriteStatus(isFav: Boolean, url: String) {
        viewModelScope.launch(coroutineDispatcherProvider.io()) {
            repository.updateFavouriteStatus(isFav, url)
        }
    }

    fun fetchFavouritePictures() {
        _viewStateLiveData.postValue(PotdViewState.Loading)
        viewModelScope.launch(coroutineDispatcherProvider.io()) {
            repository.getFavouritePictures().collect {
                val viewState = mapResultToViewState(it)
                _viewStateLiveData.postValue(viewState)
            }
        }
    }

    fun fetchPictureOfTheDay(date: String) {
        _viewStateLiveData.postValue(PotdViewState.Loading)
        viewModelScope.launch(coroutineDispatcherProvider.io()) {
            repository.getPictureOfTheDay(date).collect {
                val viewState = mapResultToViewState(it)
                _viewStateLiveData.postValue(viewState)
            }
        }
    }

    private fun mapResultToViewState(result: PotdResult): PotdViewState {
        return when (result) {
            is PotdResult.Success<*> -> PotdViewState.Success(result.data)
            is PotdResult.Empty -> PotdViewState.Error(result.message)
            is PotdResult.Error -> PotdViewState.Error(result.error)
        }
    }
}