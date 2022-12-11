package com.raywenderlich.android.busso.mvp

import android.view.View
import com.raywenderlich.android.busso.ui.view.busstop.BusStopViewModel

interface BusStopListViewBinder : ViewBinder<View> {
    fun displayBusStopList(busStopList: List<BusStopViewModel>)
    fun displayErrorMessage(msg: String)
    interface BusStopItemSelectedListener {
        fun onBusStopItemSelected(busStopViewModel: BusStopViewModel)
        fun retry()
    }
}