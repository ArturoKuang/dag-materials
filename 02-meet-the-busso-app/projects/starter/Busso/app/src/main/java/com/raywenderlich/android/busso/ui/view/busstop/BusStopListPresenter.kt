package com.raywenderlich.android.busso.ui.view.busstop

import android.view.View
import com.raywenderlich.android.busso.mvp.BusStopListViewBinder
import com.raywenderlich.android.busso.mvp.Presenter

// 1
interface BusStopListPresenter :
    Presenter<View, BusStopListViewBinder>,
    BusStopListViewBinder.BusStopItemSelectedListener  {
    // 2
    fun start()
    fun stop()
}
