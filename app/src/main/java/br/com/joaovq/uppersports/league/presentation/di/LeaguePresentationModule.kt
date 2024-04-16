package br.com.joaovq.uppersports.league.presentation.di

import br.com.joaovq.uppersports.league.presentation.viewmodel.LeagueListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val leaguePresentationModule = module {
    viewModel {
        LeagueListViewModel(get())
    }
}