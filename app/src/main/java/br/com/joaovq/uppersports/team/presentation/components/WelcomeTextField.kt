package br.com.joaovq.uppersports.team.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import br.com.joaovq.uppersports.ui.theme.LocalSpacing

@Composable
fun WelcomeTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(),
    onValueChange: (TextFieldValue) -> Unit = {},
    placeholder: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
) {
    val spacing = LocalSpacing.current
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacing.small),
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
            focusedContainerColor = MaterialTheme.colorScheme.onTertiary.copy(.3f),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = keyboardOptions
    )
}