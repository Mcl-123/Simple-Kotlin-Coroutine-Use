package com.jackie.getdatafrommockapianddatabase

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class AndroidVersionApplication : Application() {

    companion object {
        lateinit var context: Context
        private val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        Timber.plant(Timber.DebugTree())
        System.setProperty("kotlin.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")
    }
}