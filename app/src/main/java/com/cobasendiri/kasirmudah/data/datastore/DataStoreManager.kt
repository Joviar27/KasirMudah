package com.cobasendiri.kasirmudah.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.profileDataStore: DataStore<Preferences> by preferencesDataStore(name = "shop_profile")

class DataStoreManager(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val SHOP_NAME_KEY = stringPreferencesKey("shop_name")
        val SHOP_IMAGE_KEY = stringPreferencesKey("shop_image")

        @Volatile
        private var INSTANCE: DataStoreManager? = null

        fun getInstance(context: Context): DataStoreManager {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStoreManager(context.profileDataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun saveShopProfile(shopName: String, shopImage: String){
        dataStore.edit { preferences ->
            preferences[SHOP_NAME_KEY] = shopName
            preferences[SHOP_IMAGE_KEY] = shopImage
        }
    }

    val shopNameFlow: Flow<String?> =
        dataStore.data.map { preference ->
            preference[SHOP_NAME_KEY]
        }

    val shopImageFlow: Flow<String?> =
        dataStore.data.map { preference ->
            preference[SHOP_IMAGE_KEY]
        }
}