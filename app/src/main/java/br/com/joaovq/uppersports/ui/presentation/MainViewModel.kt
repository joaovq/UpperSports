package br.com.joaovq.uppersports.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.uppersports.data.local.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    val currentUser = Firebase.auth.currentUser
    var isLoading by mutableStateOf(false)
        private set
    val isNewUser = userRepository.getIsNewUser()
        .onStart { isLoading = true }
        .onCompletion { isLoading = false }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            true
        )

    fun checkIsNewUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.setIsNewUser(false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}