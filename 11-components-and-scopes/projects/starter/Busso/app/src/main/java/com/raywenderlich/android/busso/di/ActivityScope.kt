package com.raywenderlich.android.busso.di

import javax.inject.Scope

@Scope // 1
@MustBeDocumented // 2
@Retention(AnnotationRetention.RUNTIME) // 3
annotation class ActivityScope // 4