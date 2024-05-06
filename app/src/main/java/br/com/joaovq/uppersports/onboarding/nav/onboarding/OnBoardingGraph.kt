package br.com.joaovq.uppersports.onboarding.nav.onboarding

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import br.com.joaovq.uppersports.league.presentation.compose.LeagueDetailsScreen
import br.com.joaovq.uppersports.league.presentation.viewmodel.LeagueDetailViewModel
import br.com.joaovq.uppersports.onboarding.OnboardingScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.onboardingGraph(navController: NavController) {
    navigation("onboarding", "onboarding-graph") {
        composable("onboarding") {
            OnboardingScreen(
                onNavigateToLeague = {
                    navController.navigate("leagues/$it")
                }
            )
        }
        composable(
            "leagues/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val viewModel = koinViewModel<LeagueDetailViewModel>()
            val id = it.arguments?.getInt("id")
            id?.let { safeId ->
                viewModel.getLeague(safeId)
                LeagueDetailsScreen(
                    modifier = Modifier.statusBarsPadding(),
                    league = viewModel.league,
                    onPopNavigate = navController::popBackStack,
                    onEvent = viewModel::onEvent,
                    standingsResponse = viewModel.standingsLeague
                )
            }
        }
    }
}