package br.com.joaovq.uppersports.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.joaovq.uppersports.league.presentation.compose.LeagueListScreen
import br.com.joaovq.uppersports.league.presentation.viewmodel.LeagueListViewModel
import br.com.joaovq.uppersports.onboarding.nav.navDrawerItems
import br.com.joaovq.uppersports.ui.components.UpperSportsTopBar
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onNavigateToLeague: (id: Int) -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
        confirmStateChange = { value ->
            when (value) {
                DrawerValue.Closed -> true
                DrawerValue.Open -> true
            }
        }
    )
    val scope = rememberCoroutineScope()
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    val navController = rememberNavController()
    DismissibleNavigationDrawer(
        modifier = modifier,
        drawerContent = {
            DismissibleDrawerSheet {
                Column(modifier = Modifier.padding(spacing.small)) {
                    Spacer(modifier = Modifier.height(spacing.default))
                    navDrawerItems.forEachIndexed { index, drawerItem ->
                        NavigationDrawerItem(
                            label = { Text(text = stringResource(id = drawerItem.label)) },
                            selected = selectedItem == index,
                            onClick = {
                                scope.launch {
                                    navController.navigate(drawerItem.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    selectedItem = index
                                    drawerState.close()
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                /*selectedContainerColor = MaterialTheme.colorScheme.primary.copy(
                                    alpha = .9f
                                ),*/
                                selectedContainerColor = Color.Transparent,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                selectedIconColor = MaterialTheme.colorScheme.primary
                            ),
                            icon = {
                                drawerItem.icon?.let {
                                    Icon(
                                        painter = painterResource(id = it),
                                        contentDescription = stringResource(id = drawerItem.label)
                                    )
                                }
                            },
                            badge = {
                                drawerItem.badgeContent.apply {
                                    if (isNotBlank()) Text(text = this)
                                }
                            },
                            shape = RectangleShape
                        )
                    }
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = false
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
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        item {
                            /*Text(
                                modifier = Modifier.padding(spacing.default),
                                text = "Today match's",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleLarge
                            )*/
                        }
                    }
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
    UpperSportsTheme {
        OnboardingScreen()
    }
}