package com.cobasendiri.kasirmudah.util

import com.cobasendiri.kasirmudah.data.entity.ProductEntity
import com.cobasendiri.kasirmudah.data.result.ProductResult
import com.cobasendiri.kasirmudah.model.Product
import com.cobasendiri.kasirmudah.model.ProductInfo

object DataMapper {

    fun ProductResult.mapToDomain(): ProductInfo{
        return ProductInfo(
            product = this.productEntity.mapToDomain(),
            count = this.count
        )
    }

    fun ProductEntity.mapToDomain(): Product{
        return Product(
            id = this.id,
            name = this.name,
            price = this.price,
            colorCode = this.colorCode
        )
    }
}