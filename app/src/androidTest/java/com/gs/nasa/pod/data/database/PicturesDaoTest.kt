package com.gs.nasa.pod.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.gs.nasa.pod.data.model.Data
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PicturesDaoTest {
    private lateinit var picturesDao: PicturesDao
    private lateinit var picturesDatabase: PicturesDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        picturesDatabase = Room.inMemoryDatabaseBuilder(
            context, PicturesDatabase::class.java
        ).allowMainThreadQueries().build()
        picturesDao = picturesDatabase.getPicturesDao()
    }

    @Test
    fun insertPicture_expectedSingleRow() = runBlocking {
        picturesDao.savePictureOfTheDay(data)
        val result = picturesDao.getPictureOfTheDay("2022-11-22")
        Assert.assertEquals(mUrl, result.url)
    }


    @Test
    fun updateFavourite_expectedTrue() = runBlocking {
        picturesDao.savePictureOfTheDay(data)

        picturesDao.updateFavouriteStatus(true, mUrl)
        val result = picturesDao.isFavouritePictures(mUrl)
        Assert.assertEquals(true, result)
    }

    @Test
    fun getAllFavourite_expectedSingleRow() = runBlocking {
        picturesDao.savePictureOfTheDay(data)
        picturesDao.updateFavouriteStatus(true, mUrl)
        val result = picturesDao.getAllFavouritePictures()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(true, result[0].isFavourite)
    }

    @After
    fun tearDown() {
        picturesDatabase.close()
    }

    companion object {
        private val mUrl: String = "https://apod.nasa.gov/apod/image/2211/LDN1251v7social1024.png"
        private val data: Data = Data(
            "2022-11-22",
            "explanation",
            "image",
            "title",
            mUrl,
            false
        )
    }

}

