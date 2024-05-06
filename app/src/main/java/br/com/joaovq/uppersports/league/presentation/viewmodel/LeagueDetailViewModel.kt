package br.com.joaovq.uppersports.league.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.uppersports.core.utils.http.NetworkResponse
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueResponse
import br.com.joaovq.uppersports.league.data.remote.model.standings.Standing
import br.com.joaovq.uppersports.league.domain.usecase.GetLeagueById
import br.com.joaovq.uppersports.league.domain.usecase.GetStandingsLeagueUseCase
import br.com.joaovq.uppersports.league.presentation.event.LeagueDetailEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class LeagueDetailViewModel(
    private val getLeagueById: GetLeagueById,
    private val getStandingsLeagueUseCase: GetStandingsLeagueUseCase
) : ViewModel() {
    private val log = Timber.tag(this::class.java.simpleName)

    var league: LeagueResponse? by mutableStateOf(null)
        private set
    var standingsLeague: List<Standing>? by mutableStateOf(null)
        private set

    fun onEvent(leagueDetailEvent: LeagueDetailEvent) {
        when (leagueDetailEvent) {
            is LeagueDetailEvent.GetStandings -> {
                getStandingsLeague(leagueDetailEvent.season)
            }
        }
    }

    fun getLeague(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getLeagueById(id).let { networkResponse ->
                when (networkResponse) {
                    is NetworkResponse.Success -> {
                        league = networkResponse.data.response.first()
                    }

                    else -> {
                        if (networkResponse is NetworkResponse.BadRequest) {
                            log.e(networkResponse.message)
                            return@launch
                        }
                        log.e("Error ocurred in request")
                    }
                }
            }
        }
    }


    private fun getStandingsLeague(season: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getStandingsLeagueUseCase(season).let { networkResponse ->
                when (networkResponse) {
                    is NetworkResponse.Success -> {
                        standingsLeague =
                            networkResponse.data.response.league.standings.firstOrNull()
                    }

                    else -> {
                        if (networkResponse is NetworkResponse.BadRequest) {
                            log.e(networkResponse.message)
                            return@launch
                        }
                        log.e("Error ocurred in request")
                    }
                }
            }
        }
    }
}