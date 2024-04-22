package br.com.joaovq.uppersports.league.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.uppersports.core.utils.http.NetworkResponse
import br.com.joaovq.uppersports.league.data.remote.model.League
import br.com.joaovq.uppersports.league.data.remote.model.LeagueResponse
import br.com.joaovq.uppersports.league.domain.usecase.GetLeagueById
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import timber.log.Timber

class LeagueDetailViewModel(
    private val getLeagueById: GetLeagueById
) : ViewModel() {
    private val log = Timber.tag(this::class.java.simpleName)

    var league: LeagueResponse? by mutableStateOf(null)
        private set

    fun getLeague(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getLeagueById(id).let { networkResponse ->
                when (networkResponse) {
                    is NetworkResponse.Success -> {
                        league = networkResponse.data.response.firstOrNull()
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