package com.cobasendiri.kasirmudah.data.result

import androidx.room.Embedded
import com.cobasendiri.kasirmudah.data.entity.ProductEntity

data class ProductResult(
    @Embedded
    val productEntity: ProductEntity,
    val count: Int
)