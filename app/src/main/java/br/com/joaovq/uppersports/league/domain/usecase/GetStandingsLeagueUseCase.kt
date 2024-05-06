package br.com.joaovq.uppersports.league.domain.usecase

import br.com.joaovq.uppersports.data.remote.ApiSportsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetStandingsLeagueUseCase(
    private val apiSportsService: ApiSportsService,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(season: Int) = withContext(coroutineDispatcher) {
        apiSportsService.getStandingsLeague(season)
    }
}