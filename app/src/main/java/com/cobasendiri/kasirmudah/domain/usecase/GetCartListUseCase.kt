package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.data.Result
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import com.cobasendiri.kasirmudah.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetCartListUseCase(
    private val cartRepository: ICartRepository
) {
    fun invoke(searchQuery: String): Flow<Result<List<ProductInfo>>> {
        return cartRepository.getAllCartProduct(searchQuery)
            .map {
                Result.Success(it)
            }.catch {
                Result.Error(it)
            }
    }
}