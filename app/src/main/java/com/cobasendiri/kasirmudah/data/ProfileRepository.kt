package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.datastore.DataStoreManager
import com.cobasendiri.kasirmudah.data.util.CoroutineMapper.mapExceptionFlow
import com.cobasendiri.kasirmudah.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudah.domain.model.ShopProfile
import com.cobasendiri.kasirmudah.domain.repository.IProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ProfileRepository(
    private val dataStoreManager: DataStoreManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): IProfileRepository {

    companion object {
        @Volatile
        private var instance: IProfileRepository? = null

        fun getInstance(dataStoreManager: DataStoreManager): IProfileRepository {
            return instance ?: synchronized(this) {
                instance ?: ProfileRepository(dataStoreManager)
                    .also { instance = it }
            }
        }
    }

    override fun getShopProfile(): Flow<ShopProfile> {
         return combine(
             dataStoreManager.shopNameFlow,
             dataStoreManager.shopImageFlow
         ){ shopName, shopImage ->
             ShopProfile(
                 shopName ?: "",
                 shopImage ?: ""
             )
         }.mapExceptionFlow().flowOn(ioDispatcher)
    }

    override suspend fun saveShopProfile(
        shopProfile: ShopProfile
    ) = withContext(ioDispatcher){
        runMapExceptionSuspending {
            dataStoreManager.saveShopProfile(
                shopProfile.shopName,
                shopProfile.shopImage
            )
        }
    }
}