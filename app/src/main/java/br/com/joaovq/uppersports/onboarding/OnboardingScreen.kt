package br.com.joaovq.uppersports.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.joaovq.uppersports.onboarding.presentation.compose.components.OnboardingDrawerSheet
import br.com.joaovq.uppersports.league.presentation.compose.LeagueListScreen
import br.com.joaovq.uppersports.league.presentation.viewmodel.LeagueListViewModel
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.HomeScreen
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.FixturesViewModel
import br.com.joaovq.uppersports.ui.components.UpperSportsTopBar
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onNavigateToLeague: (id: Int) -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val navController = rememberNavController()
    DismissibleNavigationDrawer(
        modifier = modifier,
        drawerContent = {
            OnboardingDrawerSheet(
                selectedItem = selectedItem,
                onClickItem = { i, route ->
                    scope.launch {
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        selectedItem = i
                        drawerState.close()
                    }
                }
            )
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            modifier = Modifier.statusBarsPadding(),
            topBar = {
                UpperSportsTopBar(
                    onClickNavIcon = {
                        scope.launch {
                            if (drawerState.isOpen) drawerState.close() else drawerState.open()
                        }
                    }
                )
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                composable("home") {
                    val viewModel = koinViewModel<FixturesViewModel>()
                    val isLoading by viewModel.isLoading.collectAsState()
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        isLoading = isLoading,
                        fixtures = viewModel.fixtureResponse
                    )
                }
                composable("leagues") {
                    val leagueListViewModel = koinViewModel<LeagueListViewModel>()
                    LeagueListScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNavigateToLeague = onNavigateToLeague,
                        leagues = leagueListViewModel.paginatedLeagueResponse?.response.orEmpty(),
                        isLoading = leagueListViewModel.isLoading,
                        onEvent = leagueListViewModel::onEvent,
                        state = leagueListViewModel.state,
                    )
                }
            }
        }
    }
    BackHandler(drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }
}

@Preview
@PreviewLightDark
@Composable
fun OnboardPreview() {
    UpperSportsTheme { OnboardingScreen() }
}