package com.gs.nasa.pod.data.network

import com.gs.nasa.pod.data.model.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PicturesApiService {

    @GET("apod?api_key=rCu2dOglgtb1RcjzeutQbeDo4FRbwL8oyuBhJr6h")
    suspend fun getPictureOfTheDay(@Query("date") date: String): Response<Data>
}

