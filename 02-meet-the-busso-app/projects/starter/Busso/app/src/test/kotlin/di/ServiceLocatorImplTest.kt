package di

import androidx.test.core.app.ApplicationProvider
import com.raywenderlich.android.busso.di.LOCATION_OBSERVABLE
import com.raywenderlich.android.busso.di.ServiceLocator
import com.raywenderlich.android.busso.di.ServiceLocatorImpl
import com.raywenderlich.android.location.api.model.LocationEvent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ServiceLocatorImplTest {
    @Rule
    @JvmField
    var thrown: ExpectedException = ExpectedException.none()
    lateinit var serviceLocator: ServiceLocator

    @Before
    fun setUp() {
        serviceLocator = ServiceLocatorImpl(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun lookUp_whenObjectIsMissing_ThrowsException() {
        thrown.expect(IllegalArgumentException::class.java)
        serviceLocator.lookUp<Any>("MISSING")
    }

    @Test
    fun lookUp_locationObservable() {
        serviceLocator.lookUp<Any>(LOCATION_OBSERVABLE) as io.reactivex.Observable<LocationEvent>
    }
}