package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.model.ShopProfile
import com.cobasendiri.kasirmudah.domain.repository.IProfileRepository

class UpdateShopProfileUseCase(
    private val profileRepository: IProfileRepository
) {
    suspend fun invoke(shopProfile: ShopProfile): Result<Unit> {
        return try {
            val isNameValid = shopProfile.let {
                it.shopName.isNotBlank() && it.shopName.firstOrNull()?.isWhitespace() == false
            }
            if(!isNameValid){
                throw KasirMudahException.InvalidInputError
            }

            val result = profileRepository.saveShopProfile(shopProfile)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}