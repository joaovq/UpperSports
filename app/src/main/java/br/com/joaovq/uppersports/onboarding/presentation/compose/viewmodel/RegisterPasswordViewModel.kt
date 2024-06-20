package br.com.joaovq.uppersports.onboarding.presentation.compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.uppersports.data.local.UserRepository
import br.com.joaovq.uppersports.data.remote.AuthResponse
import br.com.joaovq.uppersports.data.remote.FirebaseAuthService
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class RegisterPasswordViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val firebaseAuthService: FirebaseAuthService,
    private val userRepository: UserRepository
) : ViewModel() {
    var password by mutableStateOf(
        TextFieldValue(text = savedStateHandle.get<String>(PASSWORD_USER_KEY).orEmpty())
    )
        private set
    private val log = Timber.tag(this::class.java.simpleName)
    private var _authResult: MutableStateFlow<AuthResult?> = MutableStateFlow(null)
    val authResult = _authResult.asStateFlow()
    var isLoading by mutableStateOf(false)
        private set
    var error: String? by mutableStateOf(null)
        private set

    fun onChangePassword(value: TextFieldValue) {
        password = value
    }

    private fun createAccount(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                isLoading = true
                firebaseAuthService.createAccount(name, email, password).onEach { response ->
                    when (response) {
                        is AuthResponse.Error -> {
                            val errorMessage = response.ex.message
                            log.e("Error message: %s", errorMessage.toString())
                            error = errorMessage
                        }

                        is AuthResponse.Success -> {
                            log.d("authResult: ${response.data}")
                            _authResult.value = response.data
                            checkIsNewUser()
                        }
                    }
                }.onCompletion { isLoading = false }.collect()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun onCompleteRegister(name: String, email: String) {
        viewModelScope.launch {
            val passwordText = password.text
            createAccount(
                name,
                email,
                passwordText
            )
            savedStateHandle["password_user"] = passwordText
        }
    }

    private fun checkIsNewUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.setIsNewUser(false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val PASSWORD_USER_KEY = "password_user"
    }
}