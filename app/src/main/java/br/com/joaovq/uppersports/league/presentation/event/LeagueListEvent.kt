package br.com.joaovq.uppersports.league.presentation.event

import androidx.compose.ui.text.input.TextFieldValue
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueType

sealed class LeagueListEvent {
    class Search(val query: TextFieldValue) : LeagueListEvent()
    class ChangeTypeLeague(val type: LeagueType) : LeagueListEvent()
}
