package com.raywenderlich.android.busso.di

import androidx.fragment.app.Fragment
import com.raywenderlich.android.busso.mvp.BusStopListViewBinder
import com.raywenderlich.android.busso.mvp.BusStopListViewBinderImpl
import com.raywenderlich.android.busso.network.BussoEndpoint
import com.raywenderlich.android.busso.ui.view.busstop.BusStopListPresenter
import com.raywenderlich.android.busso.ui.view.busstop.BusStopListPresenterImpl
import com.raywenderlich.android.location.api.model.LocationEvent
import com.raywenderlich.android.ui.navigation.Navigator
import io.reactivex.Observable

const val BUSSTOP_LIST_PRESENTER = "BusStopListPresenter"
const val BUSSTOP_LIST_VIEWBINDER = "BusStopListViewBinder"

// ...

class FragmentServiceLocator(
    val fragment: Fragment
) : ServiceLocator {

    var activityServiceLocator: ServiceLocator? = null
    var busStopListPresenter: BusStopListPresenter? = null
    var busStopListViewBinder: BusStopListViewBinder? = null

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    override fun <A : Any> lookUp(name: String): A = when (name) {
        BUSSTOP_LIST_PRESENTER -> {
            if (busStopListPresenter == null) {
                val navigator: Navigator = activityServiceLocator!!.lookUp(NAVIGATOR)
                val locationObservable: Observable<LocationEvent> = activityServiceLocator!!.lookUp(
                    LOCATION_OBSERVABLE
                )
                val bussoEndpoint: BussoEndpoint = activityServiceLocator!!.lookUp(BUSSO_ENDPOINT)
                busStopListPresenter = BusStopListPresenterImpl(
                    navigator,
                    locationObservable,
                    bussoEndpoint
                )
            }
            busStopListPresenter
        }
        BUSSTOP_LIST_VIEWBINDER -> {
            if (busStopListViewBinder == null) {
                val busStopListPresenter: BusStopListPresenter = lookUp(BUSSTOP_LIST_PRESENTER)
                busStopListViewBinder = BusStopListViewBinderImpl(busStopListPresenter)
            }
            busStopListViewBinder
        }
        else -> activityServiceLocator?.lookUp<A>(name)
            ?: throw IllegalArgumentException("No component lookup for the key: $name")
    } as A
}