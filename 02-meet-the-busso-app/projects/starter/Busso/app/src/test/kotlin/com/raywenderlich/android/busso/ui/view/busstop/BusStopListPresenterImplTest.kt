package com.raywenderlich.android.busso.ui.view.busstop

import android.os.Build
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.raywenderlich.android.busso.mvp.BusStopListViewBinder
import com.raywenderlich.android.busso.network.BussoEndpoint
import com.raywenderlich.android.location.api.model.LocationEvent
import com.raywenderlich.android.location.api.model.LocationNotAvailable
import com.raywenderlich.android.ui.navigation.Navigator
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class BusStopListPresenterImplTest {

    lateinit var presenter: BusStopListPresenter
    lateinit var navigator: Navigator
    lateinit var locationObservable: PublishSubject<LocationEvent>
    lateinit var bussoEndpoint: BussoEndpoint
    lateinit var busStopListViewBinder: BusStopListViewBinder

    @Before
    fun setUp() {
        navigator = mock()
        locationObservable = PublishSubject.create()
        bussoEndpoint = mock()
        busStopListViewBinder = mock()
        presenter = BusStopListPresenterImpl(
            navigator,
            locationObservable,
            bussoEndpoint,
        )
        presenter.bind(busStopListViewBinder)
    }

    @Test
    fun start_whenLocationNotAvailable_displayErrorMessageInvoked() {
        presenter.start()
        locationObservable.onNext(LocationNotAvailable("Provider"))
        verify(busStopListViewBinder).displayErrorMessage("Location Not Available")
    }
}
