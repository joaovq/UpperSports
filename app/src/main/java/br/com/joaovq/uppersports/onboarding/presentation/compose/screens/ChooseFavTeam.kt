package br.com.joaovq.uppersports.onboarding.presentation.compose.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion
import br.com.joaovq.uppersports.R
import br.com.joaovq.uppersports.ui.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseFavTeamScreen(
    modifier: Modifier = Modifier
) {
    TODO("Complete choose fav team user")
    Scaffold(
        modifier = modifier,
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back to last screen"
                    )
                },
                title = {
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.select_your_favorite_team),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Inter,
                            textMotion = TextMotion.Animated
                        ),
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            columns = GridCells.Fixed(2),
        ) {
        }
    }
}