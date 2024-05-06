package br.com.joaovq.uppersports.league.domain.repository

import br.com.joaovq.uppersports.core.utils.http.NetworkResponse
import br.com.joaovq.uppersports.data.remote.ApiSportsService
import br.com.joaovq.uppersports.data.remote.PaginatedResponse
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueResponse

class LeagueRepository(
    private val apiSportsService: ApiSportsService
) {
    suspend fun getAllLeagues(): NetworkResponse<PaginatedResponse<List<LeagueResponse>>> {
        return apiSportsService.getAllLeagues()
    }
}