package br.com.joaovq.uppersports.onboarding.presentation.compose.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun LiveBall(
    modifier: Modifier
) {
    val transition = rememberInfiniteTransition("Live transition")
    val scaleSize by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Live ball animate"
    )
    Box(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scaleSize
                scaleY = scaleSize
            }
            .then(modifier)
            .clip(CircleShape)
            .background(Color.Red)

    )
}