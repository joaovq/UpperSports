package br.com.joaovq.uppersports

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import br.com.joaovq.uppersports.onboarding.OnboardingScreen
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
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
                                    OnboardingScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}