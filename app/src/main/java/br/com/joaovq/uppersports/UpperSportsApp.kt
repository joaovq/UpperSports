package br.com.joaovq.uppersports

import android.app.Application
import br.com.joaovq.uppersports.core.di.coreModule
import br.com.joaovq.uppersports.data.di.dataModule
import br.com.joaovq.uppersports.league.domain.di.leagueDomainModule
import br.com.joaovq.uppersports.league.presentation.di.leaguePresentationModule
import br.com.joaovq.uppersports.onboarding.di.onboardingPresentationModule
import br.com.joaovq.uppersports.team.di.teamModule
import br.com.joaovq.uppersports.ui.di.presentationModule
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class UpperSportsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            workManagerFactory()
            androidLogger()
            modules(
                coreModule,
                presentationModule,
                dataModule,
                teamModule,
                leagueDomainModule,
                leaguePresentationModule,
                onboardingPresentationModule
            )
        }
        with(Firebase.remoteConfig) {
            setDefaultsAsync(R.xml.remote_config_defaults)
            setConfigSettingsAsync(
                remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 3000
                }
            )
        }
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    }
}