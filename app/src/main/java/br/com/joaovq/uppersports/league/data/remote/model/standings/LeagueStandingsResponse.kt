package br.com.joaovq.uppersports.league.data.remote.model.standings

data class LeagueStandingsResponse(
    val league: StandingsResponse
)

data class StandingsResponse(
    val country: String,
    val flag: String,
    val id: Int,
    val logo: String,
    val name: String,
    val season: Int,
    val standings: List<List<Standing>>
)