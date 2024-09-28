package com.ecommerce.beatiful.data.repository

import com.ecommerce.beatiful.AmazonProductQuery
import com.ecommerce.beatiful.data.client.AmazonProductClient
import com.ecommerce.beatiful.data.local.AmazonProductSearchResource
import com.ecommerce.beatiful.data.model.toAmazonProductResult
import com.ecommerce.beatiful.util.Helpers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AmazonSearchProductRepository : KoinComponent {
    private val client: AmazonProductClient by inject()
    private val resource: AmazonProductSearchResource by inject()

    suspend fun fetchAmazonResult(
        product: String,
        differenceMinutes: Int
    ): AmazonProductQuery.Data? {
        val helper = Helpers()
        val amazonData = resource.getAmazonProductSearchData()

        if (amazonData == null) {
            val data = client.fetchAmazonResult(product)
            if (data != null) {
                resource.insertAmazonProductSearchData(data)
                return data
            }
            return data
        }

        if (helper.shouldUpdateOrNoDatabase(
                timeStamp = amazonData!!.createAt,
                differenceMinutes = differenceMinutes
            )
        ) {
            resource.deleteAmazonProductSearchData()
            val data = client.fetchAmazonResult(product)
            if (data != null) {
                resource.insertAmazonProductSearchData(data)
                return data
            }

        }

        val amazonProduct: AmazonProductQuery.Data = AmazonProductQuery.Data(
            amazonProductSearchResults = AmazonProductQuery.AmazonProductSearchResults(
                productResults = AmazonProductQuery.ProductResults(
                    results = amazonData!!.results.map {
                        it.toAmazonProductResult()
                    }
                )
            )
        )
        return amazonProduct


    }

}