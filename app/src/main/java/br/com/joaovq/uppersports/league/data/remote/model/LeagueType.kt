package br.com.joaovq.uppersports.league.data.remote.model

import com.google.gson.annotations.SerializedName

enum class LeagueType(val value: String) {
    @SerializedName("League")
    LEAGUE("League"),
    @SerializedName("Cup")
    CUP("Cup");
}