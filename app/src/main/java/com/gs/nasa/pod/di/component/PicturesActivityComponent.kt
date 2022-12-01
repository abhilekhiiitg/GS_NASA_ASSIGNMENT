package com.gs.nasa.pod.di.component

import android.app.Activity
import com.gs.nasa.pod.di.PerActivity
import dagger.Component

@PerActivity
@Component(
    dependencies = [AppComponent::class]
)
interface PicturesActivityComponent {
    fun inject(activity: Activity)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): PicturesActivityComponent
    }
}
