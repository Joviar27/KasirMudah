package com.cobasendiri.kasirmudah.domain.repository

import com.cobasendiri.kasirmudah.domain.model.ShopProfile
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {

    fun getShopProfile(): Flow<ShopProfile>

    suspend fun saveShopProfile(shopProfile: ShopProfile)
}