package com.raywenderlich.android.busso.di

import android.app.Application
import com.raywenderlich.android.busso.network.BussoEndpoint
import com.raywenderlich.android.location.api.model.LocationEvent
import dagger.BindsInstance
import dagger.Component
import io.reactivex.Observable
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {

    fun locationObservable(): Observable<LocationEvent>
    fun bussoEndpoint(): BussoEndpoint

    @Component.Factory
    interface Builder {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}