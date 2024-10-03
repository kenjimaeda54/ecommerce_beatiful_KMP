package com.ecommerce.beatiful.data.repository

import com.apollographql.apollo.exception.ApolloException
import com.ecommerce.beatiful.data.client.AmazonProductClient
import com.ecommerce.beatiful.data.local.AmazonProductByCategoryResource
import com.ecommerce.beatiful.data.model.AmazonProductCategoryModel
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.model.toAmazonProductCategory
import com.ecommerce.beatiful.data.model.toAmazonProductResult
import com.ecommerce.beatiful.util.DataOrException
import com.ecommerce.beatiful.util.Helpers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AmazonProductByCategoryRepository : KoinComponent {
    private val client: AmazonProductClient by inject()
    private val resource: AmazonProductByCategoryResource by inject()

    suspend fun fetchAmazonProductByCategory(categoryId: String, differenceMinutes: Int): DataOrException<AmazonProductCategoryModel, ApolloException, Boolean> {
        val helper = Helpers()
        val amazonData = resource.getAmazonProductByCategory(categoryId)

        if (amazonData == null) {
            val response = client.fetchAmazonProductByCategory(categoryId)
            val data = response.data
            if (data != null) {
                resource.insertAmazonProductByCategory(data)

                return DataOrException(
                    data.toAmazonProductCategory(),
                    null,
                    false
                )
            }
            return DataOrException(null, response.exception!!, false)

        }
        if (helper.shouldUpdateOrNoDatabase(
                timeStamp = amazonData!!.createAt,
                differenceMinutes = differenceMinutes
            )
        ) {
            val response = client.fetchAmazonProductByCategory(categoryId)
            val data = response.data
            if (data != null) {
                resource.deleteAmazonProductByCategory(categoryId)
                resource.insertAmazonProductByCategory(data)
                return DataOrException(
                    data.toAmazonProductCategory(),
                    null,
                    false
                )
            }
        }
        return DataOrException(amazonData, null, false)
    }
}