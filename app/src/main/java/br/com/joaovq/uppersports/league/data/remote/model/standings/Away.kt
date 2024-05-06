package br.com.joaovq.uppersports.league.data.remote.model.standings

data class Away(
    val draw: Int,
    val goals: Goals,
    val lose: Int,
    val played: Int,
    val win: Int
)