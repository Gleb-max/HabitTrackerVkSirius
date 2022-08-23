package com.habit.tracker

import androidx.multidex.MultiDexApplication
import com.habit.tracker.di.DaggerApplicationComponent

class TrackerApp : MultiDexApplication() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}