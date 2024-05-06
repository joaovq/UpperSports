package br.com.joaovq.uppersports.league.data.remote.model.league

import com.google.gson.annotations.SerializedName

data class LeagueResponse(
    val country: Country,
    val league: League,
    val seasons: List<Season>
)

data class Country(
    val code: String,
    val flag: String,
    val name: String
)

data class League(
    val id: Int,
    val logo: String,
    val name: String,
    val type: LeagueType
)

data class Season(
    val coverage: Coverage,
    val current: Boolean,
    val end: String,
    val start: String,
    val year: Int
)

data class Coverage(
    val fixtures: Fixtures,
    val injuries: Boolean,
    val odds: Boolean,
    val players: Boolean,
    val predictions: Boolean,
    val standings: Boolean,
    @SerializedName("top_assists")
    val topAssists: Boolean,
    @SerializedName("top_cards")
    val topCards: Boolean,
    @SerializedName("top_scorers")
    val topScorers: Boolean
)

data class Fixtures(
    val events: Boolean,
    val lineups: Boolean,
    @SerializedName("statistics_fixtures")
    val statisticsFixtures: Boolean,
    @SerializedName("statistics_players")
    val statisticsPlayers: Boolean
)