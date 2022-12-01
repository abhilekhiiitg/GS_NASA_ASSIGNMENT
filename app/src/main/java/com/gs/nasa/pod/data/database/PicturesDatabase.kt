package com.gs.nasa.pod.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gs.nasa.pod.data.model.Data

@Database(entities = [Data::class], version = 1)
abstract class PicturesDatabase : RoomDatabase() {
    abstract fun getPicturesDao(): PicturesDao
}