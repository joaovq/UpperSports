package br.com.joaovq.uppersports.onboarding.nav.welcome

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user.ChooseFavTeamScreen
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user.RegisterEmailStepScreen
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user.RegisterNameStepScreen
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user.RegisterPasswordStepScreen
import br.com.joaovq.uppersports.onboarding.presentation.compose.viewmodel.RegisterPasswordViewModel
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.ChooseTeamViewModel
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.WelcomeViewModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

fun NavGraphBuilder.welcomeGraph(navController: NavController) {
    navigation("choose-fav-team", "new-user-graph") {
        composable("new-user") { backStackEntry ->
            val viewModel = viewModel<WelcomeViewModel>(LocalContext.current as ComponentActivity)
            RegisterNameStepScreen(
                name = viewModel.name,
                onNameChange = viewModel::onNameChange,
                onPopBackStack = navController::popBackStack,
                onComplete = {
                    navController.navigate("email-user")
                }
            )
        }
        composable("choose-fav-team") { backStackEntry ->
            val viewModel = koinViewModel<ChooseTeamViewModel>()
            val teams by viewModel.teams.collectAsState()
            val state by viewModel.state.collectAsState()
            ChooseFavTeamScreen(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                onPopBackStack = navController::popBackStack,
                teams = teams,
                state = state,
                onQueryChange = viewModel::onQueryChange,
                onClearSelectedTeams = viewModel::clearSelectedTeams,
                onSelectedTeam = viewModel::onSelectedTeam,
                onRemoveTeam = viewModel::onRemoveTeam,
                onNextStep = {
                    viewModel.saveFavTeams()
                    backStackEntry.savedStateHandle["selected_teams"] = it.toTypedArray()
                    navController.navigate("new-user")
                },
            )
        }
        composable("email-user") { _ ->
            val viewModel = viewModel<WelcomeViewModel>(LocalContext.current as ComponentActivity)
            RegisterEmailStepScreen(
                email = viewModel.email,
                onPopBackStack = navController::popBackStack,
                onEmailChange = viewModel::onEmailChange,
                onComplete = {
                    navController.navigate("password-user")
                }
            )
        }
        composable("password-user") { _ ->
            val log = Timber.tag("Password_user_route")
            val viewModel = koinViewModel<RegisterPasswordViewModel>()
            val sharedViewModel =
                viewModel<WelcomeViewModel>(LocalContext.current as ComponentActivity)
            val authResult by viewModel.authResult.collectAsState()
            LaunchedEffect(key1 = authResult) {
                log.d("auth result: ${authResult?.user}")
                authResult?.let {
                    navController.navigate("onboarding-graph") {
                        restoreState = true
                        launchSingleTop = true
                        popUpTo("onboarding-graph") {
                            inclusive = true
                        }
                    }
                }
            }
            RegisterPasswordStepScreen(
                password = viewModel.password,
                onPopBackStack = navController::popBackStack,
                onPasswordChange = viewModel::onChangePassword,
                onComplete = {
                    viewModel.onCompleteRegister(
                        sharedViewModel.name.text,
                        sharedViewModel.email.text
                    )
                },
                isLoading = viewModel.isLoading
            )
        }
    }
}