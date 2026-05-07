package com.cobasendiri.kasirmudah.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun KasirMudahTheme(
    isLightSystemBar: Boolean = true,
    content: @Composable () -> Unit,
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val view = window.decorView

            val controller = WindowCompat.getInsetsController(window, view)

            controller.isAppearanceLightStatusBars = isLightSystemBar
            controller.isAppearanceLightNavigationBars = isLightSystemBar
        }
    }
    MaterialTheme(
        typography = KasirMudahTypography,
        content = content
    )
}