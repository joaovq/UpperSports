package br.com.joaovq.uppersports.league.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.joaovq.uppersports.onboarding.nav.navDrawerItems
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
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
                    /*modifier = Modifier.clickable(
                        MutableInteractionSource(),
                        indication = null,
                        onClick = {}
                    ),*/ interactionSource = NoRippleInteractionSource(),
                    label = { Text(text = stringResource(id = drawerItem.label)) },
                    selected = selectedItem == index,
                    onClick = { onClickItem(index, drawerItem.route) },
                    colors = NavigationDrawerItemDefaults.colors(
                        /*selectedContainerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = .9f
                        ),*/
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
        }
    }
}