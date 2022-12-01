package com.gs.nasa.pod.di.module

import android.content.Context
import androidx.room.Room
import com.gs.nasa.pod.data.database.PicturesDatabase
import com.gs.nasa.pod.utils.Constants.PICTURES_DB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): PicturesDatabase {
        return Room.databaseBuilder(context, PicturesDatabase::class.java, (PICTURES_DB))
            .build()
    }
}