package com.gs.nasa.pod.di.component

import android.content.Context
import com.gs.nasa.pod.data.database.PicturesDatabase
import com.gs.nasa.pod.data.network.PicturesApiService
import com.gs.nasa.pod.di.module.CommonUtilModule
import com.gs.nasa.pod.di.module.NetworkModule
import com.gs.nasa.pod.di.module.PersistenceModule
import com.gs.nasa.pod.utils.ConnectivityManager
import com.gs.nasa.pod.utils.CoroutineDispatcherProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        PersistenceModule::class,
        CommonUtilModule::class
    ]
)
interface AppComponent {

    fun getCoroutineDispatcherProvider(): CoroutineDispatcherProvider
    fun getConnectivityManager(): ConnectivityManager
    fun getPicturesApiService(): PicturesApiService
    fun getPicturesDatabase(): PicturesDatabase
    fun getContext(): Context

    @Component.Builder
    interface Builder {
        fun appContext(@BindsInstance context: Context): Builder
        fun build(): AppComponent
    }
}
