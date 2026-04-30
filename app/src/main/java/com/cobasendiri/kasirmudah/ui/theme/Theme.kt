package com.cobasendiri.kasirmudah.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun KasirMudahTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = KasirMudahTypography,
        content = content
    )
}