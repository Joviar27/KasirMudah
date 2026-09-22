package com.cobasendiri.kasirmudah.ui.profile

import com.cobasendiri.kasirmudah.domain.model.ShopProfile

interface ProfileEvent {

    data class OnShowEditProfileDialog(
        val shopProfile: ShopProfile
    ): ProfileEvent

    data object OnDismissEditProfileDialog: ProfileEvent

    data object OnLaunchImagePicker: ProfileEvent

    data class OnEditProfile(
        val newShopProfile: ShopProfile
    ): ProfileEvent

    data object OnShowUnavailableDialog: ProfileEvent

    data object OnDismissUnavailableDialog: ProfileEvent
}