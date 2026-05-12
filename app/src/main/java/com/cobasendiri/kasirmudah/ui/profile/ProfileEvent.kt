package com.cobasendiri.kasirmudah.ui.profile

import com.cobasendiri.kasirmudah.model.ShopProfile

interface ProfileEvent {

    data class OnShowEditProfileDialog(
        val shopProfile: ShopProfile
    ): ProfileEvent

    data object OnDismissEditProfileDialog: ProfileEvent

    data class OnEditProfile(
        val shopProfile: ShopProfile
    ): ProfileEvent

    data object OnShowUnavailableDialog: ProfileEvent

    data object OnDismissUnavailableDialog: ProfileEvent
}