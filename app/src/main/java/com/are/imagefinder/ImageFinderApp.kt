package com.are.imagefinder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImageFinderApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}