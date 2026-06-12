package com.cobasendiri.kasirmudah.ui.profile

import com.cobasendiri.kasirmudah.domain.model.ShopProfile

data class ProfileState(
    val shopProfile: ShopProfile = ShopProfile("",""),
    val showEditProfileDialog: ShopProfile? = null,
    val showUnavailableDialog: Boolean = false
)