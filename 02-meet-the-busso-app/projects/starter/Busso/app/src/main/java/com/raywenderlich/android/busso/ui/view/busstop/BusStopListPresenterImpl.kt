package com.raywenderlich.android.busso.ui.view.busstop

import android.view.View
import androidx.core.os.bundleOf
import com.raywenderlich.android.busso.R
import com.raywenderlich.android.busso.mvp.BasePresenter
import com.raywenderlich.android.busso.mvp.BusStopListViewBinder
import com.raywenderlich.android.busso.network.BussoEndpoint
import com.raywenderlich.android.busso.ui.view.busarrival.BusArrivalFragment
import com.raywenderlich.android.busso.ui.view.busarrival.BusArrivalFragment.Companion.BUS_STOP_ID
import com.raywenderlich.android.location.api.model.*
import com.raywenderlich.android.ui.navigation.FragmentFactoryDestination
import com.raywenderlich.android.ui.navigation.Navigator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BusStopListPresenterImpl(
    private val navigator: Navigator,
    private val locationObservable: Observable<LocationEvent>,
    private val bussoEndpoint: BussoEndpoint
) : BasePresenter<View, BusStopListViewBinder>(), BusStopListPresenter {
    private val disposables = CompositeDisposable()

    // 2
    override fun start() {
        disposables.add(
            locationObservable
                .filter(::isLocationEvent)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleLocationEvent, ::handleError)
        )
    }

    private fun handleLocationEvent(locationEvent: LocationEvent) {
        when (locationEvent) {
            is LocationNotAvailable -> useViewBinder {
                displayErrorMessage("Location Not Available")
            }
            is LocationData -> useLocation(locationEvent.location)
        }
    }

    private fun useLocation(location: GeoLocation) {
        disposables.add(
            bussoEndpoint
                .findBusStopByLocation(location.latitude, location.longitude, 500)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(::mapBusStop)
                .subscribe(::displayBusStopList, ::handleError)
        )
    }

    private fun displayBusStopList(busStopList: List<BusStopViewModel>) {
        useViewBinder {
            displayBusStopList(busStopList)
        }
    }

    private fun handleError(throwable: Throwable) {
        useViewBinder {
            displayErrorMessage("Error: ${throwable.localizedMessage}")
        }
    }

    // 3
    override fun stop() {
        disposables.clear()
    }

    override fun onBusStopItemSelected(busStopViewModel: BusStopViewModel) {
        navigator.navigateTo(
            FragmentFactoryDestination(
                fragmentFactory = { bundle ->
                    BusArrivalFragment().apply {
                        arguments = bundle
                    }
                },
                anchorId = R.id.anchor_point,
                withBackStack = "BusArrival",
                bundle = bundleOf(
                    BUS_STOP_ID to busStopViewModel.stopId
                )
            )
        )
    }

    private fun isLocationEvent(locationEvent: LocationEvent) =
        locationEvent !is LocationPermissionRequest && locationEvent !is LocationPermissionGranted

    override fun retry() {
        start()
    }

}