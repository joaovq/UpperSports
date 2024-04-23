package br.com.joaovq.uppersports.onboarding.presentation.compose.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.joaovq.uppersports.data.remote.FixtureResponse
import br.com.joaovq.uppersports.onboarding.presentation.compose.components.LiveBall
import br.com.joaovq.uppersports.ui.utils.shimmerEffect
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    fixtureResponse: List<FixtureResponse>? = null,
    isLoading: Boolean = false
) {
    if (isLoading) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .padding(5.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Live",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    LiveBall(modifier = Modifier.size(10.dp))
                }
            }
            items(
                fixtureResponse.orEmpty(),
                key = {
                    it.fixture.id
                }
            ) { fixture ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    /*colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(.8f),
                        contentColor = MaterialTheme.colorScheme.tertiary
                    )*/
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        with(fixture.teams.home) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = name,
                                style = MaterialTheme.typography.labelSmall,
                                overflow = TextOverflow.Visible
                            )
                            AsyncImage(
                                modifier = Modifier
                                    .weight(1f)
                                    .size(30.dp),
                                model = coil.request.ImageRequest.Builder(LocalContext.current)
                                    .data(logo).build(),
                                contentDescription = "$name logo"
                            )
                        }
                        Text(
                            modifier = Modifier.weight(1f),
                            text = buildString {
                                append(fixture.goals.home.toString())
                                append(" X ")
                                append(fixture.goals.away.toString())
                            },
                            style = MaterialTheme.typography.titleSmall
                        )
                        with(fixture.teams.away) {
                            AsyncImage(
                                modifier = Modifier
                                    .weight(1f)
                                    .size(30.dp),
                                model = coil.request.ImageRequest.Builder(LocalContext.current)
                                    .data(logo).build(),
                                contentDescription = "$name logo"
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = name,
                                style = MaterialTheme.typography.labelSmall,
                                overflow = TextOverflow.Visible
                            )
                        }
                    }
                }
            }
        }
    }
}