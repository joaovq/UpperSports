package br.com.joaovq.uppersports.onboarding.presentation.compose.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.joaovq.uppersports.data.remote.FixtureResponse
import br.com.joaovq.uppersports.onboarding.presentation.compose.components.FixtureList
import br.com.joaovq.uppersports.onboarding.presentation.compose.components.FixtureLoadingList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    fixtures: List<FixtureResponse>? = null,
    isLoading: Boolean = false
) {
    if (isLoading) {
        FixtureLoadingList(
            modifier = modifier
        )
    } else {
        FixtureList(
            modifier = modifier,
            fixtureResponses = fixtures
        )
    }
}