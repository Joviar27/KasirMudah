package com.cobasendiri.kasirmudah.ui.profile

import com.cobasendiri.kasirmudah.domain.model.ShopProfile
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage

data class ProfileState(
    val shopProfile: ShopProfile = ShopProfile("",""),
    val uiMessage: UiMessage? = null,
    val showEditProfileDialog: ShopProfile? = null,
    val showUnavailableDialog: Boolean = false
)