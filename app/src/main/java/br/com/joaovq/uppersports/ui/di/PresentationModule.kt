package br.com.joaovq.uppersports.ui.di

import br.com.joaovq.uppersports.ui.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel(get()) }
}