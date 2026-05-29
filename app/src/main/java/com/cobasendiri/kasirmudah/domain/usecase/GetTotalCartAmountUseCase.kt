package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import com.cobasendiri.kasirmudah.domain.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetTotalCartAmountUseCase(
    private val cartRepository: ICartRepository
) {
    fun invoke(): Flow<Result<Double?>>{
        return cartRepository.getTotalCartAmount()
            .map {
                Result.Success(it)
            }.catch {
                Result.Error(it)
            }
    }
}