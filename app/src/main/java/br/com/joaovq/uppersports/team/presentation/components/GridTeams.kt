package br.com.joaovq.uppersports.team.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.joaovq.uppersports.team.domain.model.Team
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme

@Composable
fun GridSelectorTeams(
    modifier: Modifier = Modifier,
    teams: List<Team>,
    selectedTeams: SnapshotStateList<Team>
) {
    val spacing = LocalSpacing.current
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.small),
        columns = GridCells.Fixed(2),
    ) {
        /*TODO put selected teams in top*/
        items(teams, key = { it.id }) { team ->
            val selectedTeam by remember(selectedTeams) {
                derivedStateOf {
                    selectedTeams.contains(team)
                }
            }
            SelectorTeamCard(
                modifier = Modifier.padding(spacing.small),
                isSelected = selectedTeam,
                team = team
            ) {
                if (selectedTeam) {
                    selectedTeams.remove(team)
                } else {
                    selectedTeams.add(team)
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

@Preview
@Composable
fun GridSelectorTeamsPreview(
    @PreviewParameter(TeamsPreviewProvider::class) teams: List<Team>
) {
    UpperSportsTheme {
        GridSelectorTeams(teams = teams, selectedTeams = SnapshotStateList())
    }
}

class TeamsPreviewProvider : PreviewParameterProvider<List<Team>> {
    override val values: Sequence<List<Team>>
        get() = sequence {
            this.yield(
                listOf(
                    Team(
                        0,
                        "Vitoria",
                        "",
                        "VIT",
                        1899,
                        "Brazil"
                    )
                )
            )
        }

}