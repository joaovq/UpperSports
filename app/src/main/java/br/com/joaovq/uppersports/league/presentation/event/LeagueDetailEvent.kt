package br.com.joaovq.uppersports.league.presentation.event

sealed class LeagueDetailEvent {
    data class GetStandings(val season: Int): LeagueDetailEvent()
}
