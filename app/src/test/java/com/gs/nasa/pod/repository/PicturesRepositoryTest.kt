package com.gs.nasa.pod.repository

import android.content.Context
import com.gs.nasa.R
import com.gs.nasa.pod.data.database.PicturesDatabase
import com.gs.nasa.pod.data.model.Data
import com.gs.nasa.pod.data.network.PicturesApiService
import com.gs.nasa.pod.repository.model.PotdResult
import com.gs.nasa.pod.utils.ConnectivityManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class PicturesRepositoryTest {

    private val db: PicturesDatabase = mockk()
    private val apiService: PicturesApiService = mockk()
    private val connectivityManager: ConnectivityManager = mockk()
    private val context: Context = mockk()

    private val repository = PicturesRepository(apiService, db, connectivityManager, context)

    @Test
    fun `verify Picture Of The Day fetch from DB when data is not available`() = runBlocking {
        coEvery { connectivityManager.isInternetConnected() } returns false
        coEvery { db.getPicturesDao().getPictureOfTheDay(any()) } returns data

        val actual = repository.getPictureOfTheDay("2022-11-22").first()

        coVerify(exactly = 0) { apiService.getPictureOfTheDay(any()) }
        coVerify { db.getPicturesDao().getPictureOfTheDay(any()) }
        assertEquals(PotdResult.Success(data), actual)
    }

    @Test
    fun `verify Picture Of The Day fetch from Network when data is available`() = runBlocking {
        coEvery { connectivityManager.isInternetConnected() } returns true
        coEvery { apiService.getPictureOfTheDay(any()) } returns Response.success(data)
        coEvery { db.getPicturesDao().savePictureOfTheDay(any()) } returns Unit
        coEvery { db.getPicturesDao().isFavouritePictures(any()) } returns false

        val actual = repository.getPictureOfTheDay("2022-11-22").first()

        assertEquals(PotdResult.Success(data), actual)
    }

    @Test
    fun `verify Picture Of The Day fetch from Network when data is available media type is video`() = runBlocking {
        coEvery { connectivityManager.isInternetConnected() } returns true
        coEvery { apiService.getPictureOfTheDay(any()) } returns Response.success(dataVideoType)
        coEvery { db.getPicturesDao().savePictureOfTheDay(any()) } returns Unit
        coEvery { db.getPicturesDao().isFavouritePictures(any()) } returns false

        val actual = repository.getPictureOfTheDay("2022-11-22").first()

        assertEquals(PotdResult.Error(), actual)
    }


    @Test
    fun `verify favorites expected empty response`() = runBlocking {
        coEvery { db.getPicturesDao().getAllFavouritePictures() } returns emptyList()
        coEvery { context.getString(R.string.no_favourite_pic)} returns "No Favourite picture available"

        val actual = repository.getFavouritePictures().first()
        assertEquals(PotdResult.Empty("No Favourite picture available"), actual)
    }

    @Test
    fun `verify favorites expected non empty response`() = runBlocking {
        val output = listOf(data)
        coEvery {
            db.getPicturesDao().getAllFavouritePictures()
        } returns output

        val flow = repository.getFavouritePictures()
        val result = flow.first()
        val expected = PotdResult.Success(output)
        assertEquals(expected, result)
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
        private val dataVideoType: Data = Data(
            "2022-11-22",
            "explanation",
            "video",
            "title",
            mUrl,
            false
        )
    }
}
