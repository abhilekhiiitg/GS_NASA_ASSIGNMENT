package com.gs.nasa.pod.di.component

import com.gs.nasa.pod.di.PerActivity
import com.gs.nasa.pod.view.PicturesActivity
import dagger.Component

@PerActivity
@Component(
    dependencies = [AppComponent::class]
)
interface PicturesActivityComponent {
    fun inject(activity: PicturesActivity)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): PicturesActivityComponent
    }
}
