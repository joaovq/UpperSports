package br.com.joaovq.uppersports.onboarding.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class WelcomeViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var name by mutableStateOf(TextFieldValue(text = savedStateHandle["name-user"] ?: ""))
        private set
    var email by mutableStateOf(TextFieldValue(text = savedStateHandle["email-user"] ?: ""))
        private set
    var password by mutableStateOf(TextFieldValue(text = savedStateHandle["password-user"] ?: ""))
        private set

    fun onNameChange(value: TextFieldValue) {
        name = value
    }
    fun onEmailChange(value: TextFieldValue) {
        email = value
    }
    fun onPasswordChange(value: TextFieldValue) {
        password = value
    }
}