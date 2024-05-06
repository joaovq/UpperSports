package br.com.joaovq.uppersports.team.domain.model

data class Team(
    val id: Int,
    val name: String,
    val logoUrl: String? = null,
    val code: String? = null,
    val founded: Int,
    val country: String
)