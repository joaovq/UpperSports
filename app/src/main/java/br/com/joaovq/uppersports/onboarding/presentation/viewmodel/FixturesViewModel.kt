package br.com.joaovq.uppersports.onboarding.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.uppersports.core.utils.http.NetworkResponse
import br.com.joaovq.uppersports.data.remote.ApiSportsService
import br.com.joaovq.uppersports.data.remote.FixtureResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class FixturesViewModel(
    private val apiSportsService: ApiSportsService
) : ViewModel() {
    private val log = Timber.tag(this::class.java.simpleName)
    var fixtureResponse: List<FixtureResponse>? by mutableStateOf(null)
        private set

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isLoading = _isLoading.asStateFlow()

    init {
        getFixtures()
    }

    private fun getFixtures() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                when (val networkResponse = apiSportsService.getLiveFixtures()) {
                    is NetworkResponse.BadRequest -> log.i(this::class.java.simpleName, "Error")
                    is NetworkResponse.Error -> log.i(this::class.java.simpleName, "Error")
                    is NetworkResponse.InternalServerError -> log.i(
                        this::class.java.simpleName,
                        "Error"
                    )

                    is NetworkResponse.Success -> {
                        fixtureResponse = networkResponse.data.response
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}