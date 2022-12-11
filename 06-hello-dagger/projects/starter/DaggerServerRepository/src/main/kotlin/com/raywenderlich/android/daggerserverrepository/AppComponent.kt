package com.raywenderlich.android.daggerserverrepository

import dagger.Component

@Component(modules = [MainModule::class])
interface AppComponent {
    fun server(): Server
}