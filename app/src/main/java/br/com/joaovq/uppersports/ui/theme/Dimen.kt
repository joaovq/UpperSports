package br.com.joaovq.uppersports.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Spacing(
    val default: Dp = 16.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val large: Dp = 24.dp
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }