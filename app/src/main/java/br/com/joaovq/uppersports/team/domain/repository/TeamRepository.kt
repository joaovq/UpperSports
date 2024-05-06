package br.com.joaovq.uppersports.team.domain.repository

import br.com.joaovq.uppersports.data.remote.Team as RemoteTeam
import br.com.joaovq.uppersports.team.domain.model.Team as TeamModel
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getTeams(): Flow<List<TeamModel>>
    suspend fun getTeamsByQuery(query: String): Flow<List<TeamModel>>
    suspend fun insertTeam(team: TeamModel)
    suspend fun deleteTeam(team: TeamModel)
    suspend fun deleteAllTeams()
    suspend fun bulkInsertTeams(teams: List<RemoteTeam>)
}