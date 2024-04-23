package br.com.joaovq.uppersports.data.remote

import com.google.gson.annotations.SerializedName

data class FixtureResponse(
    val fixture: Fixture,
    val goals: Goals,
    val league: League,
    val score: Score,
    val teams: Teams
)

data class Fixture(
    val date: String,
    val id: Int,
    val periods: Periods,
    val referee: Any,
    val status: Status,
    val timestamp: Int,
    val timezone: String,
    val venue: Venue
)

data class Goals(
    val away: Int,
    val home: Int
)

data class League(
    val country: String,
    val flag: String,
    val id: Int,
    val logo: String,
    val name: String,
    val round: String,
    val season: Int
)

data class Score(
    @SerializedName("extratime")
    val extraTime: ExtraTime,
    @SerializedName("fulltime")
    val fullTime: FullTime,
    val halftime: Halftime,
    val penalty: Penalty
)

data class Teams(
    val away: AwayTeam,
    val home: HomeTeam
)

data class Periods(
    val first: Int,
    val second: Any
)

data class Status(
    val elapsed: Int,
    val long: String,
    val short: String
)

data class Venue(
    val city: String,
    val id: Int,
    val name: String
)

data class ExtraTime(
    val away: Int,
    val home: Int
)

data class FullTime(
    val away: Int,
    val home: Int
)

data class Halftime(
    val away: Int,
    val home: Int
)

data class Penalty(
    val away: Int,
    val home: Int
)

data class AwayTeam(
    val id: Int,
    val logo: String,
    val name: String,
    val winner: Boolean
)

data class HomeTeam(
    val id: Int,
    val logo: String,
    val name: String,
    val winner: Boolean
)