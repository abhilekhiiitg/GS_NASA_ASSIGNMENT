package com.gs.nasa.pod.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gs.nasa.pod.data.model.Data

@Dao
interface PicturesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePictureOfTheDay(data: Data)

    @Query("SELECT * FROM Data WHERE date =:date")
    suspend fun getPictureOfTheDay(date: String): Data

    @Query("SELECT * FROM Data WHERE isFavourite = 1")
    suspend fun getAllFavouritePictures(): List<Data>

    @Query("UPDATE Data SET isFavourite = :isFav WHERE url = :url")
    suspend fun updateFavouriteStatus(isFav: Boolean, url: String)

    @Query("SELECT isFavourite FROM Data WHERE url =:url")
    suspend fun isFavouritePictures(url: String): Boolean

}