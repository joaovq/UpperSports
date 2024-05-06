package br.com.joaovq.uppersports.onboarding.presentation.compose.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.joaovq.uppersports.R
import br.com.joaovq.uppersports.team.domain.model.Team
import br.com.joaovq.uppersports.onboarding.presentation.viewmodel.ChooseTeamState
import br.com.joaovq.uppersports.ui.theme.Inter
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseFavTeamScreen(
    modifier: Modifier = Modifier,
    teams: List<Team> = listOf(),
    state: ChooseTeamState = ChooseTeamState(),
    onPopBackStack: () -> Unit = {},
    onQueryChange: (TextFieldValue) -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val selectedTeams = remember { mutableStateListOf<Int>() }
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
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(Modifier.fillMaxSize()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.small),
                    value = state.query,
                    onValueChange = onQueryChange,
                    placeholder = { Text(text = "Search...") },
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.onTertiary.copy(.3f),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
                )
                AnimatedVisibility(visible = selectedTeams.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacing.small, horizontal = spacing.default)
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = { selectedTeams.clear() })
                            },
                        horizontalArrangement = Arrangement.spacedBy(spacing.small)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "")
                        Text(text = "Clear selected teams")
                    }
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(spacing.small),
                    columns = GridCells.Fixed(2),
                ) {
                    items(teams, key = { it.id }) { team ->
                        val selectedTeam by remember(selectedTeams) {
                            derivedStateOf {
                                selectedTeams.contains(team.id)
                            }
                        }
                        val secondaryColor = MaterialTheme.colorScheme.secondary
                        val selectedTeamModifier by remember(selectedTeam) {
                            derivedStateOf {
                                if (selectedTeam) {
                                    Modifier.border(2.dp, secondaryColor)
                                } else {
                                    Modifier
                                }
                            }
                        }
                        Card(
                            modifier = Modifier
                                .padding(spacing.small)
                                .then(selectedTeamModifier),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onTertiary.copy(.2f)
                            ),
                            onClick = {
                                if (selectedTeam) {
                                    selectedTeams.remove(team.id)
                                } else {
                                    selectedTeams.add(team.id)
                                }
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(spacing.small),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                AsyncImage(
                                    modifier = Modifier.aspectRatio(16 / 9f),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(team.logoUrl).build(),
                                    contentDescription = "${team.name} logo"
                                )
                                Text(text = team.name, textAlign = TextAlign.Center)
                            }
                        }
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                        )
                    }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small)
                    .align(Alignment.BottomCenter),
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    modifier = Modifier.padding(spacing.small),
                    text = "Confirm".uppercase()
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