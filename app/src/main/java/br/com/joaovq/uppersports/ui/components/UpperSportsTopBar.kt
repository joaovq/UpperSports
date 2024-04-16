package br.com.joaovq.uppersports.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import br.com.joaovq.uppersports.R
import br.com.joaovq.uppersports.ui.theme.Inter
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpperSportsTopBar(
    modifier: Modifier = Modifier,
    onClickNavIcon: () -> Unit = {},
    onClickNotificationIcon: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onClickNavIcon) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu hamburg"
                )
            }
        },
        title = {
            val alphaAnimation = remember { Animatable(0f) }
            LaunchedEffect(key1 = "initial-anim") {
                alphaAnimation.animateTo(
                    1f,
                    animationSpec = tween(3000)
                )
            }
            Text(
                modifier = Modifier.graphicsLayer {
                    alpha = alphaAnimation.value
                },
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Inter,
                    textMotion = TextMotion.Animated
                ),
            )
        },
        actions = {
            IconButton(onClick = onClickNotificationIcon) {
                Icon(
                    imageVector = Icons.Default.NotificationsNone,
                    contentDescription = "Notifications None"
                )
            }
        }
    )
}

@Preview
@Composable
fun UpperSportsTopBarPreview() {
    UpperSportsTheme {
        UpperSportsTopBar()
    }
}