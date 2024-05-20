package br.com.joaovq.uppersports.league.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.joaovq.uppersports.onboarding.nav.navDrawerItems
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme
import br.com.joaovq.uppersports.ui.utils.NoRippleInteractionSource

@Composable
fun OnboardingDrawerSheet(
    modifier: Modifier = Modifier,
    selectedItem: Int = 0,
    onClickItem: (Int, String) -> Unit
) {
    val spacing = LocalSpacing.current
    DismissibleDrawerSheet {
        Column(modifier = modifier.padding(spacing.small)) {
            Spacer(modifier = Modifier.height(spacing.default))
            navDrawerItems.forEachIndexed { index, drawerItem ->
                NavigationDrawerItem(
                    interactionSource = NoRippleInteractionSource(),
                    label = { Text(text = stringResource(id = drawerItem.label)) },
                    selected = selectedItem == index,
                    onClick = { onClickItem(index, drawerItem.route) },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.Transparent,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        selectedIconColor = MaterialTheme.colorScheme.background
                    ),
                    icon = {
                        val colorScheme = MaterialTheme.colorScheme.primary
                        val selectedBackground =
                            if (selectedItem == index) colorScheme else Color.Transparent
                        drawerItem.icon?.let {
                            Icon(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(selectedBackground)
                                    .padding(4.dp),
                                painter = painterResource(id = it),
                                contentDescription = stringResource(id = drawerItem.label),
                            )
                        }
                    },
                    badge = {
                        drawerItem.badgeContent.apply {
                            if (isNotBlank()) Text(text = this)
                        }
                    },
                    shape = RectangleShape
                )
            }
            Spacer(modifier = Modifier.height(spacing.default))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { /*TODO*/ })
                    },
                horizontalArrangement = Arrangement.spacedBy(
                    spacing.small,
                    Alignment.CenterHorizontally
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(text = "Sign out", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingDrawerSheetPreview() {
    UpperSportsTheme {
        OnboardingDrawerSheet { _, _ -> }
    }
}