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

    /**
     * To get picture of the day
     *
     * @param date - to get picture of the day on given date
     *
     * @returns picture of the day from server if network is available
     * OR @returns picture of the day from db is network is not available and data is present in db
     * OR @returns error - if media type is video
     * OR @returns error - if internet is not available and data is not present in db
     * OR @returns error - if server response is not successful
     */
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
                } ?: run { emit(PotdResult.Empty()) }
            } else {
                val errorMsg = Gson().fromJson(result.errorBody()?.string() ?: "", ApiError::class.java)
                emit(PotdResult.Error(errorMsg.msg ?: ""))
            }
        } else {
            val reviews = db.getPicturesDao().getPictureOfTheDay(date)
            if (reviews == null) emit(PotdResult.Empty(context.getString(R.string.no_internet))) else emit(PotdResult.Success(reviews))
        }
    }.catch {
        emit(PotdResult.Error())
    }


    /**
     * To get all Favourite Pictures marked by user
     *
     * @returns empty status if no picture is marked as favourite
     * OR
     * @returns list of favourite pictures
     */
    suspend fun getFavouritePictures() = flow {
        val favList = db.getPicturesDao().getAllFavouritePictures()
        if (favList.isEmpty()) emit(PotdResult.Empty(context.getString(R.string.no_favourite_pic)))
        else emit(PotdResult.Success(favList))
    }


    /**
     * Update the favourite status in db
     *
     * @param isFav boolean value to set the status
     * @param url - aganist which status is to be update
     */
    suspend fun updateFavouriteStatus(isFav: Boolean, url: String) {
        db.getPicturesDao().updateFavouriteStatus(isFav, url)
    }

}