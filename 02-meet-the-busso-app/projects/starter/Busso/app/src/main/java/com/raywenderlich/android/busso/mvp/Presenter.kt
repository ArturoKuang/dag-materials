package com.raywenderlich.android.busso.mvp

interface Presenter<V, VB : ViewBinder<V>> {
    fun bind(viewBinder: VB)
    fun unbind()
}