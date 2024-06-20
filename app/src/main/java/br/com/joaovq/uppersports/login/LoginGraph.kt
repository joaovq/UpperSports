package br.com.joaovq.uppersports.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.loginGraph(navController: NavController) {
    navigation(startDestination = "login", route = "login-graph") {
        composable("login") {}
        composable("register") {}
    }
}