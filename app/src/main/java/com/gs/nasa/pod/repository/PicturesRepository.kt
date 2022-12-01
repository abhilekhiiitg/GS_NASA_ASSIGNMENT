package com.gs.nasa.pod.repository

import android.content.Context
import com.google.gson.Gson
import com.gs.nasa.R
import com.gs.nasa.pod.data.database.PicturesDatabase
import com.gs.nasa.pod.data.model.ApiError
import com.gs.nasa.pod.data.network.PicturesApiService
import com.gs.nasa.pod.repository.model.PotdResult
import com.gs.nasa.pod.utils.ConnectivityManager
import com.gs.nasa.pod.utils.Constants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PicturesRepository @Inject constructor(
    private val apiService: PicturesApiService,
    private val db: PicturesDatabase,
    private val connectivityManager: ConnectivityManager,
    private val context: Context,
) {

    suspend fun getPictureOfTheDay(date: String) = flow {
        if (connectivityManager.isInternetConnected()) {
            val result = apiService.getPictureOfTheDay(date)
            if (result.isSuccessful) {
                result.body()?.let { potd ->
                    if (potd.media_type == Constants.MEDIA_TYPE_IMAGE) {
                        db.getPicturesDao().savePictureOfTheDay(potd)
                        potd.isFavourite = db.getPicturesDao().isFavouritePictures(potd.url)
                        emit(PotdResult.Success(potd))
                    } else emit(PotdResult.Error(context.getString(R.string.no_video_support)))
                } ?: run {
                    emit(PotdResult.Empty())
                }
            } else {
                val errorMessage =
                    Gson().fromJson(result.errorBody()?.string() ?: "", ApiError::class.java)
                emit(PotdResult.Error(errorMessage.msg ?: ""))
            }
        } else {
            val reviews = db.getPicturesDao().getPictureOfTheDay(date)
            if (reviews == null) emit(PotdResult.Empty(context.getString(R.string.no_internet))) else emit(
                PotdResult.Success(
                    reviews
                )
            )
        }
    }.catch {
        emit(PotdResult.Error())
    }

    suspend fun getFavouritePictures() = flow {
        val favList = db.getPicturesDao().getAllFavouritePictures()
        if (favList.isEmpty()) emit(PotdResult.Empty(context.getString(R.string.no_favourite_pic)))
        else emit(PotdResult.Success(favList))
    }

    suspend fun updateFavouriteStatus(isFav: Boolean, url: String) {
        db.getPicturesDao().updateFavouriteStatus(isFav, url)
    }

}