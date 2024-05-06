package br.com.joaovq.uppersports.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.uppersports.data.local.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    val isNewUser = userRepository.getIsNewUser()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            true
        )

    fun checkIsNewUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.setIsNewUser(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}