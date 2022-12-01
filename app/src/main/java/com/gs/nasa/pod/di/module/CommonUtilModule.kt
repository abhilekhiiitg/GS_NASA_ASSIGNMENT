package com.gs.nasa.pod.di.module

import android.content.Context
import com.gs.nasa.pod.utils.ConnectivityManager
import com.gs.nasa.pod.utils.CoroutineDispatcherProvider
import com.gs.nasa.pod.utils.CoroutineDispatcherProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommonUtilModule {

    @Provides
    @Singleton
    fun providesConnectivityManager(context: Context) = ConnectivityManager(context)

    @Provides
    @Singleton
    fun providesCoroutineDispatcherProvider(): CoroutineDispatcherProvider =
        CoroutineDispatcherProviderImpl()
}
