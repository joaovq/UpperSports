package br.com.joaovq.uppersports.core.di

import br.com.joaovq.uppersports.data.local.UserLocalDataSource
import br.com.joaovq.uppersports.data.local.UserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val namedIODispatcher = "IODispatcher"

val coreModule = module {
    single(named(namedIODispatcher)) { Dispatchers.IO }
    single { UserLocalDataSource(this.androidContext()) }
    single { UserRepository(get()) }
}