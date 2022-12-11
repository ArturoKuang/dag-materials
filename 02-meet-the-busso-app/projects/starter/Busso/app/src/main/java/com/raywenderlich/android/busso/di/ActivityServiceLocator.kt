package com.raywenderlich.android.busso.di

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.raywenderlich.android.ui.navigation.NavigatorImpl

const val NAVIGATOR = "Navigator"
const val FRAGMENT_LOCATOR_FACTORY = "FragmentLocatorFactory"

val activityServiceLocatorFactory: (ServiceLocator) -> ServiceLocatorFactory<AppCompatActivity> =
    { fallbackServiceLocator: ServiceLocator ->
        { activity: AppCompatActivity ->
            ActivityServiceLocator(activity).apply {
                applicationServiceLocator = fallbackServiceLocator
                Log.d("ServiceLocator", this.toString())
            }
        }
    }


val fragmentServiceLocatorFactory: (ServiceLocator) -> ServiceLocatorFactory<Fragment> =
    { fallbackServiceLocator: ServiceLocator ->
        { fragment: Fragment ->
            FragmentServiceLocator(fragment).apply {
                activityServiceLocator = fallbackServiceLocator
                Log.d("ServiceLocator", this.toString())
            }
        }
    }


class ActivityServiceLocator(
    private val activity: AppCompatActivity
) : ServiceLocator {
    var applicationServiceLocator: ServiceLocator? = null

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    override fun <A : Any> lookUp(name: String): A = when (name) {
        NAVIGATOR -> NavigatorImpl(activity)
        FRAGMENT_LOCATOR_FACTORY -> fragmentServiceLocatorFactory(this)
        else -> applicationServiceLocator?.lookUp<A>(name)
            ?: throw IllegalArgumentException("No component lookup for the key: $name")
    } as A
}