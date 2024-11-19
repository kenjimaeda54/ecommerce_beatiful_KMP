package com.ecommerce.beatiful.data.client

import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.AmazonProductSearchQuery
import com.ecommerce.beatiful.data.client.contracts.ApolloClientApi
import com.ecommerce.beatiful.data.client.contracts.AmazonProduct
import com.ecommerce.beatiful.util.DataOrException

class AmazonProductImplementation(private  val client: ApolloClientApi): AmazonProduct {

    override suspend fun fetchAmazonSearchProduct(product: String): DataOrException<AmazonProductSearchQuery.Data, String,Boolean> {
       val clientApollo =  client.apollo.query(AmazonProductSearchQuery(searchTerm = product)).execute()
        if(clientApollo.exception != null){
            return DataOrException(null, clientApollo.exception?.message ?:  "Error fetching message AppoloException",false)
        }
       return DataOrException(clientApollo.data!!,null,false)

    }

    override suspend fun fetchAmazonProductByCategory(categoryId: String): DataOrException<AmazonProductByCategoryQuery.Data, String,Boolean> {
        val clientApollo =  client.apollo.query(AmazonProductByCategoryQuery(categoryId = categoryId)).execute()

        if(clientApollo.exception != null){
            return DataOrException(null,clientApollo.exception?.message ?:  "Error fetching message AppoloException",false)
        }

        return DataOrException(clientApollo.data!!,null,false)
    }


}