package com.cobasendiri.kasirmudah.ui.profile

import com.cobasendiri.kasirmudah.model.ShopProfile

data class ProfileState(
    val shopProfile: ShopProfile,
    val showEditProfileDialog: ShopProfile?,
    val showUnavailableDialog: Boolean
)