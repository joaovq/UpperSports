package br.com.joaovq.uppersports.league.presentation.state

import androidx.compose.ui.text.input.TextFieldValue
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueType

data class LeagueListState(
    val query: TextFieldValue = TextFieldValue(),
    val type: LeagueType? = null
)
