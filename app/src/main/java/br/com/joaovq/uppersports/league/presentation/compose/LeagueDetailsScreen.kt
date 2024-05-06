package br.com.joaovq.uppersports.league.presentation.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.joaovq.uppersports.R
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueResponse
import br.com.joaovq.uppersports.league.data.remote.model.standings.Standing
import br.com.joaovq.uppersports.league.presentation.event.LeagueDetailEvent
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LeagueDetailsScreen(
    modifier: Modifier = Modifier,
    league: LeagueResponse? = null,
    standingsResponse: List<Standing>? = null,
    onPopNavigate: () -> Unit,
    onEvent: (LeagueDetailEvent) -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val currentSeason by remember(league) {
        derivedStateOf { league?.seasons?.find { it.current } }
    }
    var selectedTabIndex: Int by remember { mutableStateOf(0) }
    val tabs = buildList {
        add("All")
        add("Table")
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.small)
                ) {
                    val (ibPop, imageLeague) = createRefs()
                    IconButton(
                        modifier = Modifier
                            .constrainAs(ibPop) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                            .padding(4.dp),
                        onClick = onPopNavigate
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                    AsyncImage(
                        modifier = Modifier
                            .constrainAs(imageLeague) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .size(60.dp)
                            .clip(CircleShape),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(league?.league?.logo)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Inside,
                        placeholder = debugPlaceholder(R.drawable.ic_home),
                    )
                }
                Text(
                    text = league?.league?.name.orEmpty(),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = buildString {
                        append(currentSeason?.year.toString())
                        append(" - ")
                        append(league?.country?.name.toString())
                    },
                    style = MaterialTheme.typography.labelMedium
                )
                PrimaryTabRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing.default),
                    selectedTabIndex = selectedTabIndex,
                    divider = {}
                ) {
                    tabs.forEachIndexed { i, label ->
                        Tab(
                            selected = selectedTabIndex == i,
                            onClick = { selectedTabIndex = i },
                            selectedContentColor = MaterialTheme.colorScheme.secondary,
                            unselectedContentColor = MaterialTheme.colorScheme.onTertiary,
                        ) {
                            Text(text = label)
                        }
                    }
                }
            }
        }
    ) {
        when (selectedTabIndex) {
            0 -> {
                LazyColumn(modifier = Modifier.padding(it)) {
                    item {

                    }
                }
            }

            1 -> {
                LaunchedEffect(selectedTabIndex) {
                    when (selectedTabIndex) {
                        1 -> onEvent(LeagueDetailEvent.GetStandings(currentSeason?.year ?: 0))
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(spacing.small),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Rank")
                            Text(text = "Points")
                        }
                    }
                    items(standingsResponse.orEmpty()) { standing ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(spacing.small),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
                                Text(text = standing.rank.toString())
                                AsyncImage(
                                    modifier = Modifier.size(20.dp),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(standing.team.logo).build(),
                                    contentDescription = null
                                )
                                Text(text = standing.team.name)
                            }
                            Text(text = standing.points.toString())
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LeagueDetailsPreview() {
    UpperSportsTheme {
        LeagueDetailsScreen(onEvent = {}, onPopNavigate = {})
    }
}

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }