package br.com.joaovq.uppersports.onboarding.presentation.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import br.com.joaovq.uppersports.data.remote.FixtureResponse
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FixtureList(
    modifier: Modifier = Modifier,
    fixtureResponses: List<FixtureResponse>? = null
) {
    val spacing = LocalSpacing.current
    val fixturesGroupedByLeague by remember(fixtureResponses) {
        derivedStateOf { fixtureResponses?.groupBy { it.league } }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(.5f))
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
                    modifier = Modifier.padding(horizontal = spacing.small),
                    text = "Live",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                LiveBall(modifier = Modifier.size(10.dp))
            }
        }
        fixturesGroupedByLeague?.forEach { (league, fixtures) ->
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = spacing.small,
                            start = 4.dp,
                            end = 4.dp
                        )
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(MaterialTheme.colorScheme.surface),
                ) {
                    Row(
                        modifier = Modifier.padding(spacing.small),
                        horizontalArrangement = Arrangement.spacedBy(spacing.small),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .testTag("league-logo")
                                .size(20.dp),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(league.logo).build(),
                            contentDescription = "${league.name} logo"
                        )
                        Text(text = league.name, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            items(items = fixtures, key = { it.fixture.id ?: 0 }) { fixture ->
                FixtureCard(fixture = fixture)
            }
        }
    }
}