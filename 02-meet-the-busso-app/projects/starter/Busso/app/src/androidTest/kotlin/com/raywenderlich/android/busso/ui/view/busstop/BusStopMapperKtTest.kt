package com.raywenderlich.android.busso.ui.view.busstop

import com.raywenderlich.android.busso.model.BusStop
import com.raywenderlich.android.location.api.model.GeoLocation
import org.junit.Assert.assertEquals
import org.junit.Test

class BusStopMapperKtTest {

    @Test
    fun mapBusStop() {
        val busStop = BusStop(
            id = "1",
            name = "Stop 1",
            location = GeoLocation(51.509865, -0.118092),
            direction = null,
            indicator = null,
            distance = 100.0f
        )

        val busStopViewModel = mapBusStop(busStop)
        val busStopViewModelExpected = BusStopViewModel(
            "1",
            "Stop 1",
            "",
            "",
            "100 m"
        )

        assertEquals(busStopViewModelExpected, busStopViewModel)
    }

    @Test
    fun testMapBusStop() {
        val busStop1 = BusStop(
            id = "1",
            name = "Stop 2",
            location = GeoLocation(51.509865, -0.118092),
            direction = null,
            indicator = null,
            distance = 100.0f
        )

        val busStop2 = BusStop(
            id = "1",
            name = "Stop 1",
            location = GeoLocation(41.509865, 20.118092),
            direction = "N",
            indicator = "inbound",
            distance = null
        )

        val busStop3 = BusStop(
            id = "1",
            name = "Stop 1",
            location = GeoLocation(21.509865, 10.118092),
            direction = "S",
            indicator = null,
            distance = 10.0f
        )

        val busStopList = listOf(busStop1, busStop2, busStop3)
        val busStopViewModelList = mapBusStop(busStopList)

        val expectedBusStopViewModelListExpected = listOf(
            BusStopViewModel(
                "1",
                "Stop 2",
                "",
                "",
                "100 m"
            ),
            BusStopViewModel(
                "1",
                "Stop 1",
                "N",
                "inbound",
                "--"
            ),
            BusStopViewModel(
                "1",
                "Stop 1",
                "S",
                "",
                "10 m"
            )
        )

        assertEquals(expectedBusStopViewModelListExpected, busStopViewModelList)
    }
}