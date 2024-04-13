package br.com.joaovq.uppersports

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class UpperSportsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules()
        }
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    }
}