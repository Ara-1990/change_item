package com.the.changeitem
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChangeItemApplication : Application() {

        override fun onCreate() {
            super.onCreate()
        }
    }
