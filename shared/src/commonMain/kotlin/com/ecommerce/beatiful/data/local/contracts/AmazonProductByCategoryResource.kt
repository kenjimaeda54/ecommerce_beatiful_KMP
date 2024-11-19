package com.ecommerce.beatiful.data.local.contracts

import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.data.model.AmazonProductCategoryModel

interface  AmazonProductByCategoryResource {
    fun getAmazonProductByCategory(categoryId: String): AmazonProductCategoryModel?
    fun insertAmazonProductByCategory(data: AmazonProductByCategoryQuery.Data)
    fun deleteAmazonProductByCategory(categoryId: String)
}