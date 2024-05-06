package br.com.joaovq.uppersports.team.data.datasource.local

import br.com.joaovq.uppersports.team.data.local.dao.TeamDao
import br.com.joaovq.uppersports.team.data.local.entity.TeamEntity

class TeamLocalDatasource(
    private val teamDao: TeamDao
) {
    fun getTeams() = teamDao.getTeams()
    fun getTeamsById(id: Int) = teamDao.getTeamById(id)
    fun getTeamsByQuery(query: String) = teamDao.getTeamsByQuery(query)
    suspend fun createTeam(teamEntity: TeamEntity) = teamDao.insertTeam(teamEntity)
    suspend fun deleteTeam(teamEntity: TeamEntity) = teamDao.deleteTeam(teamEntity)
    suspend fun clearTable() = teamDao.deleteAll()
    suspend fun bulkTeams(teams: List<TeamEntity>) = teamDao.upsertTeams(teams)
}