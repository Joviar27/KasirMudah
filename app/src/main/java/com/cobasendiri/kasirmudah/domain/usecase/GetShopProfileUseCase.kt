package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.model.ShopProfile
import com.cobasendiri.kasirmudah.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetShopProfileUseCase(
    private val profileRepository: IProfileRepository
) {
    fun invoke(): Flow<Result<ShopProfile>>{
        return profileRepository.getShopProfile().map {
            Result.Success(it)
        }.catch {
            Result.Error(it)
        }
    }
}