package br.com.joaovq.uppersports.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun NavigationBackIconButton(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit = {},
    icon: ImageVector = Icons.AutoMirrored.Default.ArrowBack,
    contentDescription: String? = "Navigation back"
) {
    IconButton(
        modifier = modifier,
        onClick = onPopBackStack
    ) { Icon(icon, contentDescription = contentDescription) }
}