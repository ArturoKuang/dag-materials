package com.raywenderlich.android.busso

import android.app.Application
import android.content.Context
import com.raywenderlich.android.busso.di.ApplicationComponent
import com.raywenderlich.android.busso.di.DaggerApplicationComponent

// 1
class Main : Application() {
    // 2
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        // 3
        appComponent = DaggerApplicationComponent
            .factory()
            .create(this)
    }
}
// 4
val Context.appComp: ApplicationComponent
    get() = (applicationContext as Main).appComponent