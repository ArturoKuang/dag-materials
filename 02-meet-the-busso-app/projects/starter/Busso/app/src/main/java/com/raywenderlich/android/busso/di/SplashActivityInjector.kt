package com.raywenderlich.android.busso.di

import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.busso.SplashActivity
import com.raywenderlich.android.busso.lookUp

object SplashActivityInjector : Injector<SplashActivity> {
    override fun inject(target: SplashActivity) {
        val activityServiceLocator = target.lookUp<ServiceLocatorFactory<AppCompatActivity>>(
            ACTIVITY_LOCATION_FACTORY
        ).invoke(target)

        target.locationObservable = activityServiceLocator.lookUp(LOCATION_OBSERVABLE)
        target.navigator = activityServiceLocator.lookUp(NAVIGATOR)
    }
}