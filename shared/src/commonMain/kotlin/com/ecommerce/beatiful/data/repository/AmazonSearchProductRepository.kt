package com.ecommerce.beatiful.data.repository

import com.ecommerce.beatiful.AmazonProductQuery
import com.ecommerce.beatiful.data.client.AmazonProductClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AmazonSearchProductRepository: KoinComponent {
    private val client: AmazonProductClient by inject()

    suspend fun fetchAmazonResult(product: String): AmazonProductQuery.Data? {
        return client.fetchAmazonResult(product)
    }

}