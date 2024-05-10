package br.com.joaovq.uppersports.onboarding.nav.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user.ChooseFavTeamScreen
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user.RegisterEmailStepScreen
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user.RegisterNameStepScreen
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.ChooseTeamViewModel
import br.com.joaovq.uppersports.team.domain.model.Team
import br.com.joaovq.uppersports.team.presentation.components.WelcomeTextField
import br.com.joaovq.uppersports.ui.components.NavigationBackIconButton
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.welcomeGraph(navController: NavController) {
    navigation("choose-fav-team", "new-user-graph") {
        composable("new-user") { backStackEntry ->
            var name by remember {
                mutableStateOf(
                    TextFieldValue(
                        text = backStackEntry.savedStateHandle.get<String>("name_user")
                            .orEmpty()
                    )
                )
            }
            RegisterNameStepScreen(
                name = name,
                onNameChange = { value -> name = value },
                onPopBackStack = navController::popBackStack,
                onComplete = {
                    backStackEntry.savedStateHandle["name_user"] = name.text
                    navController.navigate("email-user")
                }
            )
        }
        composable("choose-fav-team") { backStackEntry ->
            val viewModel = koinViewModel<ChooseTeamViewModel>()
            val teams by viewModel.teams.collectAsState()
            val state by viewModel.state.collectAsState()
            val selectedTeams =
                backStackEntry.savedStateHandle.get<Array<Team>>("selected_teams")?.toList()
            ChooseFavTeamScreen(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                onPopBackStack = navController::popBackStack,
                teams = teams,
                state = state,
                onQueryChange = viewModel::onQueryChange,
                selectedTeams = selectedTeams.orEmpty(),
                onNextStep = {
                    backStackEntry.savedStateHandle["selected_teams"] = it.toTypedArray()
                    navController.navigate("new-user")
                }
            )
        }
        composable("email-user") { backStackEntry ->
            var email by remember {
                mutableStateOf(
                    TextFieldValue(
                        text = backStackEntry.savedStateHandle.get<String>("email_user")
                            .orEmpty()
                    )
                )
            }
            RegisterEmailStepScreen(
                email = email,
                onPopBackStack = navController::popBackStack,
                onEmailChange = {value -> email = value },
                onComplete = {
                    backStackEntry.savedStateHandle["email_user"] = email.text
                    navController.navigate("email-user")
                }
            )
        }
    }
}