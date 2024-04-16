package br.com.joaovq.uppersports.league.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import br.com.joaovq.uppersports.league.data.remote.model.Country
import br.com.joaovq.uppersports.league.data.remote.model.League
import br.com.joaovq.uppersports.league.data.remote.model.LeagueResponse
import br.com.joaovq.uppersports.league.data.remote.model.LeagueType
import br.com.joaovq.uppersports.league.presentation.event.LeagueListEvent
import br.com.joaovq.uppersports.league.presentation.state.LeagueListState
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.Locale

@Composable
fun LeagueListScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    leagues: List<LeagueResponse>,
    onEvent: (LeagueListEvent) -> Unit,
    state: LeagueListState
) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.default, vertical = spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All leagues",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            if (leagues.isNotEmpty()) Text(text = leagues.size.toString())
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.default, vertical = spacing.small),
            value = state.query,
            onValueChange = { onEvent(LeagueListEvent.Search(it)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            placeholder = {
                Text(text = "Search league...", style = MaterialTheme.typography.labelSmall)
            },
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = spacing.default,
                    vertical = spacing.small
                ),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            LeagueType.values().forEach {
                FilterChip(
                    selected = it.value == state.type?.value,
                    onClick = { onEvent(LeagueListEvent.ChangeTypeLeague(it)) },
                    label = { Text(text = it.value.uppercase(Locale.getDefault())) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        }
        if (!isLoading) {
            LazyColumn(modifier = Modifier) {
                items(
                    leagues,
                    key = {
                        it.league.id
                    }
                ) { leagueResponse ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing.default),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(spacing.small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                AsyncImage(
                                    modifier = Modifier.widthIn(max = 20.dp),
                                    model = ImageRequest
                                        .Builder(LocalContext.current)
                                        .data(leagueResponse.league.logo)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                                AsyncImage(
                                    modifier = Modifier.size(20.dp),
                                    model = ImageRequest
                                        .Builder(LocalContext.current)
                                        .data(leagueResponse.country.flag)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Text(
                                text = leagueResponse.league.name,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Text(
                            text = leagueResponse.league.type.value,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

class LeagueListPreviewParameterProvider : PreviewParameterProvider<List<LeagueResponse>> {
    override val values: Sequence<List<LeagueResponse>>
        get() = sequenceOf(
            listOf(
                LeagueResponse(
                    Country("br", "https://localhost:8080", "England"),
                    League(
                        10, "logourl", "Premier league", LeagueType.CUP
                    ),
                    listOf()
                )
            )
        )
}

@Preview
@Composable
fun LeagueListPreview(
    @PreviewParameter(LeagueListPreviewParameterProvider::class) leagues: List<LeagueResponse>
) {
    UpperSportsTheme {
        LeagueListScreen(
            leagues = leagues,
            onEvent = {},
            state = LeagueListState()
        )
    }
}