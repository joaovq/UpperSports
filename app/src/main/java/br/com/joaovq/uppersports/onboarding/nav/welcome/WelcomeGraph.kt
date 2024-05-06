package br.com.joaovq.uppersports.onboarding.nav.welcome

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.ChooseFavTeamScreen
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.ChooseTeamViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.welcomeGraph(navController: NavController) {
    navigation("choose-fav-team", "new-user-graph") {
        composable("new-user") {}
        composable("choose-fav-team") {
            val viewModel = koinViewModel<ChooseTeamViewModel>()
            val teams by viewModel.teams.collectAsState()
            val state by viewModel.state.collectAsState()
            ChooseFavTeamScreen(
                onPopBackStack = navController::popBackStack,
                teams = teams,
                state = state,
                onQueryChange = viewModel::onQueryChange
            )
        }
    }
}