package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository
import kotlinx.coroutines.flow.catch

class GetProductLisUseCase(
    private val productRepository: IProductRepository
){
    fun invoke(searchQuery: String): Flow<Result<List<ProductInfo>>>{
       return productRepository.getAllProducts(searchQuery)
            .map {
                if(it==null){
                    Result.Error(KasirMudahException.UnknownError(null))
                }else{
                    Result.Success(it)
                }
            }.catch{
                emit(Result.Error(it))
            }
    }
}