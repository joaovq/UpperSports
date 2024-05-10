package br.com.joaovq.uppersports.onboarding.presentation.compose.screens.new_user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import br.com.joaovq.uppersports.team.presentation.components.WelcomeTextField
import br.com.joaovq.uppersports.ui.components.NavigationBackIconButton
import br.com.joaovq.uppersports.ui.theme.LocalSpacing

@Composable
fun RegisterEmailStepScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit = {},
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit = {},
    onComplete: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
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
                        text = "Register your e-mail",
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
                WelcomeTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = {
                        Text(text = "name@example.com.br")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    )
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.small),
                    onClick = onComplete,
                    shape = MaterialTheme.shapes.medium,
                    enabled = email.text.isNotBlank()
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