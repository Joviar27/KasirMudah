package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.room.CartDao
import com.cobasendiri.kasirmudah.data.room.ProductDao

class ProductRepository(
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {


}