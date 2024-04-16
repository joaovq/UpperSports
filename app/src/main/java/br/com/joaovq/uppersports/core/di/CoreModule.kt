package br.com.joaovq.uppersports.core.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val namedIODispatcher = "IODispatcher"

val coreModule = module {
    single(named(namedIODispatcher)) {
        Dispatchers.IO
    }
}