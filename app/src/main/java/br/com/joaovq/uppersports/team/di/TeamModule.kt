package br.com.joaovq.uppersports.team.di

import br.com.joaovq.uppersports.team.data.datasource.local.TeamLocalDatasource
import br.com.joaovq.uppersports.team.data.local.TeamDatabase
import br.com.joaovq.uppersports.team.data.repository.TeamRepositoryImpl
import br.com.joaovq.uppersports.team.domain.repository.TeamRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val teamModule = module {
    single { TeamDatabase.getInstance(androidContext()) }
    single { get<TeamDatabase>().teamDao() }
    single { TeamLocalDatasource(get()) }
    single<TeamRepository> { TeamRepositoryImpl(get(), get()) }
}