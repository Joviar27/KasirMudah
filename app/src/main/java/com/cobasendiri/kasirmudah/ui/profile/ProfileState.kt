package com.cobasendiri.kasirmudah.ui.profile

import com.cobasendiri.kasirmudah.domain.model.ShopProfile

data class ProfileState(
    val shopProfile: ShopProfile,
    val showEditProfileDialog: ShopProfile?,
    val showUnavailableDialog: Boolean
)