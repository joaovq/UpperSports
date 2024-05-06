package br.com.joaovq.uppersports.onboarding.presentation.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.com.joaovq.uppersports.ui.utils.shimmerEffect

@Composable
fun FixtureLoadingList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(30.dp)
                        .padding(2.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(15.dp))
                        .shimmerEffect()
                )
            }
        }
        items(10) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .shimmerEffect()
            )
        }
    }
}