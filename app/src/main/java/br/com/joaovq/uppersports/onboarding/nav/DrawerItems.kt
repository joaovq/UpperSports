package br.com.joaovq.uppersports.onboarding.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import br.com.joaovq.uppersports.R

class DrawerItem(
    @DrawableRes val icon: Int? = null,
    @StringRes val label: Int,
    val badgeContent: String = "",
    val route: String
)

internal val navDrawerItems = listOf(
    DrawerItem(
        R.drawable.ic_home,
        R.string.label_home,
        route = "home"
    ),
    DrawerItem(
        R.drawable.ic_trophy,
        R.string.label_leagues,
        route = "leagues"
    )
)