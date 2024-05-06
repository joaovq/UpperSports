package br.com.joaovq.uppersports

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.joaovq.uppersports.onboarding.nav.onboarding.onboardingGraph
import br.com.joaovq.uppersports.onboarding.nav.welcome.welcomeGraph
import br.com.joaovq.uppersports.ui.presentation.MainViewModel
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    private val mainViewModel by inject<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val isNewUser by mainViewModel.isNewUser.collectAsState()
            val startDestination by remember(isNewUser) {
                derivedStateOf {
                    if (isNewUser) "new-user-graph" else "onboarding-graph"
                }
            }
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
                            startDestination = startDestination
                        ) {
                            welcomeGraph(navController)
                            onboardingGraph(navController)
                        }
                    }
                }
            }
        }
    }
}