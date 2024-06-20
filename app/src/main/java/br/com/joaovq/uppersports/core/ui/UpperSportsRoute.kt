package br.com.joaovq.uppersports.core.ui

sealed class UpperSportsRoute(val route: String) {
    data object OnboardingGraph : UpperSportsRoute("onboarding-graph")
    data object Onboarding : UpperSportsRoute("onboarding")
    data object WelcomeGraph : UpperSportsRoute("welcome-graph")
    data object ChooseFavTeam : UpperSportsRoute("choose-fav-team")
    data object LoginGraph : UpperSportsRoute("login-graph")
}
