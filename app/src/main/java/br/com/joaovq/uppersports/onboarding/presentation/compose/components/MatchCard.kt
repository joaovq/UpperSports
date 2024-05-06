package br.com.joaovq.uppersports.onboarding.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.joaovq.uppersports.data.remote.FixtureResponse
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import coil.compose.AsyncImage

@Composable
fun FixtureCard(
    modifier: Modifier = Modifier,
    fixture: FixtureResponse
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(spacing.small)
                .testTag("card-match-row")
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
                    overflow = TextOverflow.Visible,
                    color = MaterialTheme.colorScheme.secondary
                )
                AsyncImage(
                    modifier = Modifier
                        .weight(.9f)
                        .testTag("home-team-logo-image")
                        .size(30.dp),
                    model = coil.request.ImageRequest.Builder(LocalContext.current)
                        .data(logo).build(),
                    contentDescription = "$name logo"
                )
            }
            Text(
                modifier = Modifier.weight(1.1f),
                text = buildString {
                    append(fixture.goals.home.toString())
                    append(" X ")
                    append(fixture.goals.away.toString())
                },
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
            with(fixture.teams.away) {
                AsyncImage(
                    modifier = Modifier
                        .weight(.9f)
                        .size(30.dp),
                    model = coil.request.ImageRequest.Builder(LocalContext.current)
                        .data(logo).build(),
                    contentDescription = "$name logo"
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = name,
                    style = MaterialTheme.typography.labelSmall,
                    overflow = TextOverflow.Visible,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}