package br.com.joaovq.uppersports

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.joaovq.uppersports.login.loginGraph
import br.com.joaovq.uppersports.onboarding.nav.onboarding.onboardingGraph
import br.com.joaovq.uppersports.onboarding.nav.welcome.welcomeGraph
import br.com.joaovq.uppersports.ui.presentation.MainViewModel
import br.com.joaovq.uppersports.ui.presentation.NavState
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private val mainViewModel by inject<MainViewModel>()
    private val log = Timber.tag(this::class.java.simpleName)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        splashScreen.setKeepOnScreenCondition {
            mainViewModel.state == NavState.LOADING
        }
        setContent {
            val navController = rememberNavController()
            val isNewUser by mainViewModel.isNewUser.collectAsState()
            log.d("Is new user: $isNewUser")
            val startDestination by mainViewModel.startDestination.collectAsState()
            KoinContext {
                UpperSportsTheme(dynamicColor = false) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ) {
                        startDestination?.let {
                            NavHost(
                                navController = navController,
                                startDestination = it
                            ) {
                                welcomeGraph(navController)
                                onboardingGraph(navController)
                                loginGraph(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}