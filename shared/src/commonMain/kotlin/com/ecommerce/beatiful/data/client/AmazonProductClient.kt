package com.ecommerce.beatiful.data.client

import com.ecommerce.beatiful.AmazonProductQuery

class AmazonProductClient(private  val client: IApolloClient) {


    suspend fun fetchAmazonResult(product: String): AmazonProductQuery.Data? {
       return client.apollo.query(AmazonProductQuery(searchTherm = product)).execute().data

    }
}