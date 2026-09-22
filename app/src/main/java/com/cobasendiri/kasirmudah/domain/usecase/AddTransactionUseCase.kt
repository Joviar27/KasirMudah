package com.cobasendiri.kasirmudah.domain.usecase

import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.domain.repository.ICartRepository
import com.cobasendiri.kasirmudah.domain.repository.IProfileRepository
import com.cobasendiri.kasirmudah.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.firstOrNull

class AddTransactionUseCase(
    private val transactionRepository: ITransactionRepository,
    private val cartRepository: ICartRepository,
    private val profileRepository: IProfileRepository
) {

    suspend fun invoke(): Result<Unit>{
        return try {
            val transactionItems = cartRepository.getProductsTotal()
            if(transactionItems.isEmpty()){
                throw KasirMudahException.TransactionAmountInvalidError
            }

            val transactionTotal = cartRepository.getTotalCartAmount().firstOrNull()
                ?: transactionItems.sumOf { it.itemTotal }

            if(transactionTotal == 0L){
                throw KasirMudahException.TransactionAmountInvalidError
            }

            val shopName = profileRepository.getShopProfile().firstOrNull()?.shopName ?: ""

            val result = transactionRepository.insertNewTransaction(
                transactionItems,
                transactionTotal,
                shopName
            )
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}