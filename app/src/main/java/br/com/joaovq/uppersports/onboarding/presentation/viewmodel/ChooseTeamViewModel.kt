package br.com.joaovq.uppersports.onboarding.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.uppersports.core.utils.http.NetworkResponse
import br.com.joaovq.uppersports.data.remote.ApiSportsService
import br.com.joaovq.uppersports.data.remote.TeamResponse
import br.com.joaovq.uppersports.team.domain.model.Team
import br.com.joaovq.uppersports.team.domain.repository.TeamRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.coroutineContext
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class ChooseTeamViewModel(
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val log = Timber.tag(this::class.java.simpleName)
    private val _query = MutableStateFlow(TextFieldValue())
    val state = _query.map { ChooseTeamState(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2_000),
            ChooseTeamState()
        )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val teams: StateFlow<List<Team>> = _query.debounce(1000).flatMapLatest {
        val search = it.text.ifEmpty { null }
        teamRepository.getTeamsByQuery(search.orEmpty())
    }.catch {
        it.printStackTrace()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), listOf())

    fun onQueryChange(query: TextFieldValue) {
        viewModelScope.launch {
            currentCoroutineContext().cancel()
            _query.value = query
        }
    }
}

@Stable
data class ChooseTeamState(
    val query: TextFieldValue = TextFieldValue()
)