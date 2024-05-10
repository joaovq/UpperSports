package br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import br.com.joaovq.uppersports.R
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.ChooseTeamState
import br.com.joaovq.uppersports.team.domain.model.Team
import br.com.joaovq.uppersports.team.presentation.components.GridSelectorTeams
import br.com.joaovq.uppersports.team.presentation.components.WelcomeTextField
import br.com.joaovq.uppersports.ui.theme.Inter
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseFavTeamScreen(
    modifier: Modifier = Modifier,
    teams: List<Team> = listOf(),
    selectedTeams: List<Team> = listOf(),
    state: ChooseTeamState = ChooseTeamState(),
    onPopBackStack: () -> Unit = {},
    onNextStep: (selectedTeams: List<Team>) -> Unit = {},
    onQueryChange: (TextFieldValue) -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val selectedTeamsState = remember { mutableStateListOf<Team>().apply { addAll(selectedTeams) } }
    Scaffold(
        modifier = modifier,
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onPopBackStack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back to last screen"
                        )
                    }
                },
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(spacing.small)
                    ) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(R.string.select_your_favorite_team),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Inter,
                                textMotion = TextMotion.Animated
                            ),
                        )
                    }
                },
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small),
                onClick = { onNextStep(selectedTeamsState.toList()) },
                shape = MaterialTheme.shapes.medium,
                enabled = selectedTeamsState.isNotEmpty()
            ) {
                Text(
                    modifier = Modifier.padding(spacing.small),
                    text = "Confirm".uppercase()
                )
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(Modifier.fillMaxSize()) {
                WelcomeTextField(
                    value = state.query,
                    onValueChange = onQueryChange,
                    placeholder = {
                        Text(text = "Search...")
                    }
                )
                AnimatedVisibility(visible = selectedTeamsState.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacing.small, horizontal = spacing.default)
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = { selectedTeamsState.clear() })
                            },
                        horizontalArrangement = Arrangement.spacedBy(spacing.small)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "")
                        Text(text = "Clear selected teams")
                    }
                }
                GridSelectorTeams(
                    teams = teams,
                    selectedTeams = selectedTeamsState
                )
            }
        }
    }
}

@Preview
@Composable
fun ChooseFavTeamPreview() {
    UpperSportsTheme {
        ChooseFavTeamScreen()
    }
}