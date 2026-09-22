package com.cobasendiri.kasirmudah.ui.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BoxScope.AlertBarAnimatedVisibility(
    isVisible: Boolean,
    enterTransition: EnterTransition = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
    exitTransition: ExitTransition = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
    content: @Composable (() -> Unit)
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = enterTransition,
        exit = exitTransition,
        modifier = Modifier
            .align(Alignment.TopCenter)
            .statusBarsPadding()
    ) {
        content.invoke()
    }
}