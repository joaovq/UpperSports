package br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.com.joaovq.uppersports.team.presentation.components.WelcomeTextField
import br.com.joaovq.uppersports.ui.components.NavigationBackIconButton
import br.com.joaovq.uppersports.ui.theme.LocalSpacing
import br.com.joaovq.uppersports.ui.utils.rememberLauncherFocusRequester

@Composable
fun RegisterPasswordStepScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit = {},
    password: TextFieldValue,
    onPasswordChange: (TextFieldValue) -> Unit = {},
    isLoading: Boolean = false,
    onComplete: () -> Unit = {},
    error: String? = null
) {
    val spacing = LocalSpacing.current
    val focusRequester = rememberLauncherFocusRequester()
    var isVisiblePassword by remember { mutableStateOf(false) }
    val icon by remember(isVisiblePassword) {
        derivedStateOf {
            if (isVisiblePassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility
        }
    }
    LaunchedEffect(key1 = focusRequester) { focusRequester.requestFocus() }
    val passwordTransformation by remember(isVisiblePassword) {
        derivedStateOf {
            if (isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation()
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        modifier = modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    NavigationBackIconButton(
                        onPopBackStack = onPopBackStack
                    )
                }
            )
        }
    ) {
        AnimatedVisibility(visible = isLoading) {
            Dialog(onDismissRequest = {}, properties = DialogProperties()) {
                CircularProgressIndicator()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing.default)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.default),
                    verticalArrangement = Arrangement.spacedBy(spacing.small)
                ) {
                    Text(
                        text = "Register your password",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "need your email for register",
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
                val isError = password.text.trim().length < 5
                WelcomeTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = password,
                    onValueChange = onPasswordChange,
                    placeholder = { Text(text = "password") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    isError = isError,
                    trailingIcon = {
                        IconButton(onClick = { isVisiblePassword = !isVisiblePassword }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Visibility password"
                            )
                        }
                    },
                    visualTransformation = passwordTransformation
                )
                var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
                WelcomeTextField(
                    value = confirmPassword,
                    onValueChange = { value -> confirmPassword = value },
                    placeholder = { Text(text = "confirm your password") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    isError = confirmPassword != password,
                    trailingIcon = {
                        IconButton(onClick = { isVisiblePassword = !isVisiblePassword }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Visibility password"
                            )
                        }
                    },
                    visualTransformation = passwordTransformation,
                    enabled = !isError
                )
                AnimatedVisibility(visible = error != null) {
                    if (error != null) Text(
                        modifier = Modifier.padding(horizontal = spacing.default),
                        text = error,
                        style = MaterialTheme.typography.bodySmall.copy(Color.Red.copy(.6f))
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.small),
                    onClick = onComplete,
                    shape = MaterialTheme.shapes.medium,
                    enabled = !isError && confirmPassword == password
                ) {
                    Text(
                        modifier = Modifier.padding(spacing.small),
                        text = "Confirm".uppercase()
                    )
                }
            }
        }
    }
}