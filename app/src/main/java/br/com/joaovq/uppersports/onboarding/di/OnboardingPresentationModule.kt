package br.com.joaovq.uppersports.onboarding.di

import br.com.joaovq.uppersports.onboarding.presentation.compose.viewmodel.RegisterPasswordViewModel
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.ChooseTeamViewModel
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.FixturesViewModel
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {
    viewModel { FixturesViewModel(get()) }
    viewModel { ChooseTeamViewModel(get(), get(), get()) }
    viewModel { RegisterPasswordViewModel(get(),get(), get()) }
    viewModel { WelcomeViewModel(get()) }
}