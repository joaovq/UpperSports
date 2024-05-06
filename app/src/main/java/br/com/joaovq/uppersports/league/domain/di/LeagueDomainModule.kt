package br.com.joaovq.uppersports.league.domain.di

import br.com.joaovq.uppersports.core.di.namedIODispatcher
import br.com.joaovq.uppersports.league.domain.repository.LeagueRepository
import br.com.joaovq.uppersports.league.domain.usecase.GetAllLeagues
import br.com.joaovq.uppersports.league.domain.usecase.GetLeagueById
import br.com.joaovq.uppersports.league.domain.usecase.GetStandingsLeagueUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val leagueDomainModule = module {
    single { LeagueRepository(get()) }
    single { GetAllLeagues(get(), get(named(namedIODispatcher))) }
    single { GetLeagueById(get(), get(named(namedIODispatcher))) }
    single { GetStandingsLeagueUseCase(get(), get(named(namedIODispatcher))) }
}