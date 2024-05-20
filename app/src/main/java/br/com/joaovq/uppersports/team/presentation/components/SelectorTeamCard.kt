package br.com.joaovq.uppersports.team.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.joaovq.uppersports.team.domain.model.Team
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SelectorTeamCard(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    team: Team,
    onClick: (team: Team) -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val mediumShape = MaterialTheme.shapes.medium
    val selectedTeamModifier by remember(isSelected) {
        derivedStateOf {
            if (isSelected) {
                Modifier
                    .border(2.dp, secondaryColor,mediumShape)
            } else {
                Modifier
            }
        }
    }
    Card(
        modifier = modifier
            .then(selectedTeamModifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary.copy(.2f)
        ),
        onClick = { onClick(team) }
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