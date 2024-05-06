package br.com.joaovq.uppersports.league.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.uppersports.core.utils.http.NetworkResponse
import br.com.joaovq.uppersports.data.remote.PaginatedResponse
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueResponse
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueType
import br.com.joaovq.uppersports.league.domain.usecase.GetAllLeagues
import br.com.joaovq.uppersports.league.presentation.event.LeagueListEvent
import br.com.joaovq.uppersports.league.presentation.state.LeagueListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class LeagueListViewModel(
    private val getAllLeagues: GetAllLeagues
) : ViewModel() {

    private val log = Timber.tag(this::class.java.simpleName)

    var state by mutableStateOf(LeagueListState())
    var paginatedLeagueResponse by mutableStateOf<PaginatedResponse<List<LeagueResponse>>?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    private var searchScopeJob: Job? = null

    fun onEvent(event: LeagueListEvent) {
        when (event) {
            is LeagueListEvent.Search -> {
                onQueryChange(event.query)
            }

            is LeagueListEvent.ChangeTypeLeague -> onTypeFilterChange(event.type)
        }
    }

    private fun onQueryChange(value: TextFieldValue) {
        state = state.copy(
            query = value,
            type = null
        )
        getLeagues()
    }

    private fun onTypeFilterChange(type: LeagueType) {
        state = state.copy(
            query = TextFieldValue(),
            type = type
        )
        getLeagues()
    }

    private fun getLeagues() {
        searchScopeJob?.cancel()
        searchScopeJob = viewModelScope.launch {
            delay(400)
            try {
                isLoading = true
                getAllLeagues(state.query.text, state.type).apply {
                    when (this) {
                        is NetworkResponse.BadRequest -> {
                            log.e("Bad request in network response")
                        }

                        is NetworkResponse.Error -> {
                            log.e("Error in network response")
                        }

                        is NetworkResponse.InternalServerError -> {
                            log.e("Internal server error in network response")
                        }

                        is NetworkResponse.Success -> {
                            paginatedLeagueResponse = this.data
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchScopeJob?.cancel()
    }
}