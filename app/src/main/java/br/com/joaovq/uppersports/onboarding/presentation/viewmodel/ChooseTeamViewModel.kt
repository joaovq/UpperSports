package br.com.joaovq.uppersports.onboarding.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.uppersports.data.local.UserRepository
import br.com.joaovq.uppersports.team.domain.model.Team
import br.com.joaovq.uppersports.team.domain.repository.TeamRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class ChooseTeamViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val teamRepository: TeamRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val log = Timber.tag(this::class.java.simpleName)
    private val _query = MutableStateFlow(TextFieldValue())
    private val _selectedTeams: MutableStateFlow<Set<Team>> =
        MutableStateFlow(savedStateHandle.get<List<Team>>("selected_teams")?.toSet() ?: setOf())
    val state = combine(_query, _selectedTeams) { query, teams ->
        val chooseTeamState = ChooseTeamState(query, teams.toList())
        log.d("Actual state: $chooseTeamState")
        chooseTeamState
    }.stateIn(
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

    fun onSelectedTeam(selectedTeam: Team) {
        if (!_selectedTeams.value.any { it.id == selectedTeam.id }) {
            _selectedTeams.value += selectedTeam
        }
    }

    fun onRemoveTeam(selectedTeam: Team) {
        _selectedTeams.value -= selectedTeam
    }

    fun saveFavTeams() {
        viewModelScope.launch {
            try {
                userRepository.setFavTeams(_selectedTeams.value.toList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clearSelectedTeams() {
        viewModelScope.launch {
            try {
                _selectedTeams.value = setOf()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@Stable
data class ChooseTeamState(
    val query: TextFieldValue = TextFieldValue(),
    val selectedTeams: List<Team> = listOf()
)