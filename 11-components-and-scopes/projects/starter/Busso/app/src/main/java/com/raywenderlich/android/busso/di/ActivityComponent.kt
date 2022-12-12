package com.raywenderlich.android.busso.di

import android.app.Activity
import com.raywenderlich.android.busso.ui.view.main.MainActivity
import com.raywenderlich.android.busso.ui.view.splash.SplashActivity
import com.raywenderlich.android.ui.navigation.Navigator
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [ActivityModule::class],
    dependencies = [ApplicationComponent::class] // 1
)
@ActivityScope
interface ActivityComponent {

    fun inject(activity: SplashActivity)

    fun inject(activity: MainActivity)

    fun navigator(): Navigator

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: Activity,
            applicationComponent: ApplicationComponent // 2
        ): ActivityComponent
    }
}

