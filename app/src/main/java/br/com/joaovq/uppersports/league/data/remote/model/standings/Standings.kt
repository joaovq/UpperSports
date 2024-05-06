package br.com.joaovq.uppersports.league.data.remote.model.standings

import com.google.gson.annotations.SerializedName

data class Standing(
    val all: All,
    val away: Away,
    val description: String,
    val form: String,
    val goalsDiff: Int,
    val group: String,
    val home: Home,
    val points: Int,
    val rank: Int,
    val status: String,
    val team: Team,
    val update: String
)

data class Goals(
    val against: Int,
    @SerializedName("for")
    val goalsFor: Int
)