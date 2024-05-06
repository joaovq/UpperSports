package br.com.joaovq.uppersports.league.domain.usecase

import br.com.joaovq.uppersports.core.utils.http.NetworkResponse
import br.com.joaovq.uppersports.data.remote.ApiSportsService
import br.com.joaovq.uppersports.data.remote.PaginatedResponse
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueResponse
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllLeagues(
    private val apiSportsService: ApiSportsService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        search: String = "",
        type: LeagueType? = null
    ): NetworkResponse<PaginatedResponse<List<LeagueResponse>>> =
        withContext(coroutineDispatcher) {
            apiSportsService.getAllLeagues(search, type)
        }
}