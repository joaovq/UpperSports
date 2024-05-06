package br.com.joaovq.uppersports.data.remote

data class TeamResponse(
    val team: Team,
    val venue: Venue
)

data class Team(
    val code: String? = null,
    val country: String,
    val founded: Int,
    val id: Int,
    val logo: String? = null,
    val name: String,
    val national: Boolean
)