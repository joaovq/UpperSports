package br.com.joaovq.uppersports.onboarding.di

import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.ChooseTeamViewModel
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.FixturesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {
    viewModel { FixturesViewModel(get()) }
    viewModel { ChooseTeamViewModel(get()) }
}