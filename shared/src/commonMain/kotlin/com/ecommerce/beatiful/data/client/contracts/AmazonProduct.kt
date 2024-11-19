package com.ecommerce.beatiful.data.client.contracts

import com.apollographql.apollo.exception.ApolloException
import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.AmazonProductSearchQuery
import com.ecommerce.beatiful.util.DataOrException

interface AmazonProduct {
    suspend fun fetchAmazonSearchProduct(product: String): DataOrException<AmazonProductSearchQuery.Data, String, Boolean>
    suspend fun fetchAmazonProductByCategory(categoryId: String):DataOrException<AmazonProductByCategoryQuery.Data, String,Boolean>
}