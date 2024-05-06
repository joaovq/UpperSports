package br.com.joaovq.uppersports.team.domain.mapper

import br.com.joaovq.uppersports.team.data.local.entity.TeamEntity
import br.com.joaovq.uppersports.team.domain.model.Team
import br.com.joaovq.uppersports.data.remote.Team as RemoteTeam

fun TeamEntity.toTeam() = Team(
    this.id,
    name,
    logoUrl,
    code,
    founded,
    country
)

fun RemoteTeam.toEntity() = TeamEntity(
    id,
    name,
    logo,
    code,
    founded,
    country
)