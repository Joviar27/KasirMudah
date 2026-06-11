package com.cobasendiri.kasirmudah.nav

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object MainTabs : Screen

    @Serializable
    object Shop : Screen

    @Serializable
    object History: Screen

    @Serializable
    object Profile: Screen

    @Serializable
    object ReceiptDraft: Screen

    @Serializable
    object ReceiptDetail: Screen
}