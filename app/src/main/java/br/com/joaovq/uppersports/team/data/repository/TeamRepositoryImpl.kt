package br.com.joaovq.uppersports.team.data.repository

import br.com.joaovq.uppersports.core.utils.http.NetworkResponse
import br.com.joaovq.uppersports.data.remote.ApiSportsService
import br.com.joaovq.uppersports.team.data.datasource.local.TeamLocalDatasource
import br.com.joaovq.uppersports.team.data.local.entity.TeamEntity
import br.com.joaovq.uppersports.team.domain.mapper.toEntity
import br.com.joaovq.uppersports.team.domain.mapper.toTeam
import br.com.joaovq.uppersports.team.domain.model.Team
import br.com.joaovq.uppersports.data.remote.Team as RemoteTeam
import br.com.joaovq.uppersports.team.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class TeamRepositoryImpl(
    private val teamLocalDatasource: TeamLocalDatasource,
    private val apiSportsService: ApiSportsService
) : TeamRepository {
    private val log = Timber.tag(this::class.java.simpleName)

    override suspend fun getTeamsByQuery(query: String) =
        try {
            apiSportsService.getTeams(query).let {
                when (it) {
                    is NetworkResponse.Success -> {
                        val teams = it.data.response.map { teamResponse -> teamResponse.team }
                        teamLocalDatasource.bulkTeams(teams.map(RemoteTeam::toEntity))
                    }
                    else -> log.e("Error in request")
                }
                teamLocalDatasource.getTeamsByQuery(query).map { storageTeams ->
                    storageTeams.map(TeamEntity::toTeam)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyFlow()
        }


    override fun getTeams(): Flow<List<Team>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTeam(team: Team) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTeam(team: Team) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTeams() {
        TODO("Not yet implemented")
    }

    override suspend fun bulkInsertTeams(teams: List<br.com.joaovq.uppersports.data.remote.Team>) {
        TODO("Not yet implemented")
    }
}