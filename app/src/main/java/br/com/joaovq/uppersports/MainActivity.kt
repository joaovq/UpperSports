package br.com.joaovq.uppersports

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import br.com.joaovq.uppersports.league.data.remote.model.League
import br.com.joaovq.uppersports.league.presentation.compose.LeagueDetailsScreen
import br.com.joaovq.uppersports.league.presentation.viewmodel.LeagueDetailViewModel
import br.com.joaovq.uppersports.onboarding.OnboardingScreen
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            KoinContext {
                UpperSportsTheme(
                    dynamicColor = false
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "onboarding-graph"
                        ) {
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
                                            onPopNavigate = navController::popBackStack
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}