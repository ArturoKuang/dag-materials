package com.raywenderlich.android.busso.mvp

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.android.busso.R
import com.raywenderlich.android.busso.ui.events.OnItemSelectedListener
import com.raywenderlich.android.busso.ui.view.busstop.BusStopListAdapter
import com.raywenderlich.android.busso.ui.view.busstop.BusStopViewModel

class BusStopListViewBinderImpl(
    private val busStopItemSelectedListener: BusStopListViewBinder.BusStopItemSelectedListener? = null
) : BusStopListViewBinder {
    private lateinit var busStopRecyclerView: RecyclerView
    private lateinit var busStopAdapter: BusStopListAdapter

    override fun init(rootView: View) {
        busStopRecyclerView = rootView.findViewById(R.id.busstop_recyclerview)
        busStopAdapter = BusStopListAdapter(object : OnItemSelectedListener<BusStopViewModel> {
            override fun invoke(position: Int, selectedItem: BusStopViewModel) {
                busStopItemSelectedListener?.onBusStopItemSelected(selectedItem)
            }
        })

        initRecyclerView(busStopRecyclerView)
    }

    override fun displayBusStopList(busStopList: List<BusStopViewModel>) {
        busStopAdapter.submitList(busStopList)
    }

    override fun displayErrorMessage(msg: String) {
        Snackbar.make(busStopRecyclerView, msg, Snackbar.LENGTH_LONG)
            .setAction(R.string.message_retry) {
                busStopItemSelectedListener?.retry()
            }.show()
    }

    private fun initRecyclerView(busStopRecyclerView: RecyclerView) {
        busStopRecyclerView.apply {
            adapter = busStopAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}