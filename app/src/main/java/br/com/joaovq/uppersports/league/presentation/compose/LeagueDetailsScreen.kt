package br.com.joaovq.uppersports.league.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.joaovq.uppersports.league.data.remote.model.League
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun LeagueDetailsScreen(
    modifier: Modifier = Modifier,
    league: League? = null,
    onPopNavigate: () -> Unit
) {
    val spacing = LocalSpacing.current
    Scaffold(
        modifier = modifier,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.default),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.small)
                ) {
                    val (ibPop, imageLeague) = createRefs()
                    IconButton(
                        modifier = Modifier.constrainAs(ibPop) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        },
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
                            .data(league?.logo)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Inside
                    )
                }
                Text(text = league?.name.orEmpty(), style = MaterialTheme.typography.titleMedium)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
            }
        }
    }
}