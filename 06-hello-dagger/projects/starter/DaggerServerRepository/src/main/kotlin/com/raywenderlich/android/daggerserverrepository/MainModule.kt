package com.raywenderlich.android.daggerserverrepository

import dagger.Provides

@dagger.Module
object MainModule {
    @Provides
    fun provideRepository(): Repository = FakeRepository()
}