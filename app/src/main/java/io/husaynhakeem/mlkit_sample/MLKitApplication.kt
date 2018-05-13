package io.husaynhakeem.mlkit_sample

import android.app.Application


class MLKitApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance : MLKitApplication
    }
}