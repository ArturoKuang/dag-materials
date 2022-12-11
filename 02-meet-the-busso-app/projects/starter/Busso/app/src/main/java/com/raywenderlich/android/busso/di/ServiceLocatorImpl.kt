package com.raywenderlich.android.busso.di

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import com.raywenderlich.android.busso.permission.GeoLocationPermissionCheckerImpl
import com.raywenderlich.android.location.rx.provideRxLocationObservable

const val LOCATION_OBSERVABLE = "LocationObservable"
const val ACTIVITY_LOCATION_FACTORY = "ActivityLocationFactory"

class ServiceLocatorImpl(
    val context: Context
) : ServiceLocator {

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val geoLocationPermissionChecker = GeoLocationPermissionCheckerImpl(context)
    private val locationObservable = provideRxLocationObservable(locationManager, geoLocationPermissionChecker)

    @Suppress("UNCHECKED_CAST")
    @SuppressLint("ServiceCast")
    override fun <A : Any> lookUp(name: String): A {
        return when (name) {
            LOCATION_OBSERVABLE -> locationObservable
            ACTIVITY_LOCATION_FACTORY -> activityServiceLocatorFactory(this)
            else -> throw IllegalArgumentException("No component lookup for the key: $name")
        } as A
    }
}