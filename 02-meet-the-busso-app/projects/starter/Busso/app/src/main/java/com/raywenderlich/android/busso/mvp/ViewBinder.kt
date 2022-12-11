package com.raywenderlich.android.busso.mvp

interface ViewBinder<V> {
    fun init(rootView: V)
}