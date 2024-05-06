package br.com.joaovq.uppersports.team.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_tb")
data class TeamEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val logoUrl: String? = null,
    val code: String? = null,
    val founded: Int,
    val country: String
)