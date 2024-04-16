package br.com.joaovq.uppersports

import android.app.Application
import br.com.joaovq.uppersports.core.di.coreModule
import br.com.joaovq.uppersports.data.di.dataModule
import br.com.joaovq.uppersports.league.domain.di.leagueDomainModule
import br.com.joaovq.uppersports.league.presentation.di.leaguePresentationModule
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.extension.coroutinesEngine
import org.koin.core.lazyModules
import timber.log.Timber

class UpperSportsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                coreModule,
                dataModule,
                leagueDomainModule,
                leaguePresentationModule
            )
        }
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    }
}