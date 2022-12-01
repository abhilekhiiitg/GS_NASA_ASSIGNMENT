package com.gs.nasa.pod.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["url"])
data class Data(
    val date: String,
    val explanation: String,
    val media_type: String,
    val title: String,
    val url: String,
    var isFavourite: Boolean
)