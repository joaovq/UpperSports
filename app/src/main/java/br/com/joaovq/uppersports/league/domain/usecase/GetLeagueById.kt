package br.com.joaovq.uppersports.league.domain.usecase

import br.com.joaovq.uppersports.data.remote.ApiSportsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLeagueById(
    private val apiSportsService: ApiSportsService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(id: Int) = withContext(coroutineDispatcher) {
        apiSportsService.getLeagueById(id)
    }
}