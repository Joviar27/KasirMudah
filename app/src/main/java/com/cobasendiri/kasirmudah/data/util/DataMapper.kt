package com.cobasendiri.kasirmudah.data.util

import com.cobasendiri.kasirmudah.data.entity.ProductEntity
import com.cobasendiri.kasirmudah.data.result.ProductResult
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.ProductDraft
import com.cobasendiri.kasirmudah.domain.model.ProductInfo

object DataMapper {

    fun List<ProductResult>.mapListToDomain(): List<ProductInfo>{
        return this.map { productResults ->
            productResults.mapToDomain()
        }
    }

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

    fun Product.mapToEntity(): ProductEntity{
        return ProductEntity(
            id = this.id,
            name = this.name,
            price = this.price,
            colorCode = this.colorCode
        )
    }

    //Always wrap with try catch
    fun ProductDraft.mapToEntity(): ProductEntity{
        return ProductEntity(
            id = this.id,
            name = this.name,
            price = this.price.toLong(),
            colorCode = this.colorCode
        )
    }
}