package com.cobasendiri.kasirmudah.nav

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    object Shop : Screen

    @Serializable
    object History: Screen

    @Serializable
    object Profile: Screen
}