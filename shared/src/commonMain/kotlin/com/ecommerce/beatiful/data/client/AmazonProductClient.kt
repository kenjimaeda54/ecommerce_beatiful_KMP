package com.ecommerce.beatiful.data.client

import com.apollographql.apollo.exception.ApolloException
import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.AmazonProductSearchQuery
import com.ecommerce.beatiful.util.DataOrException

class AmazonProductClient(private  val client: IApolloClient) {

    suspend fun fetchAmazonSearchProduct(product: String): DataOrException<AmazonProductSearchQuery.Data, ApolloException,Boolean> {
       val clientApollo =  client.apollo.query(AmazonProductSearchQuery(searchTerm = product)).execute()
        if(clientApollo.exception != null){
            return DataOrException(null,clientApollo.exception!!,false)
        }
       return DataOrException(clientApollo.data!!,null,false)

    }

    suspend fun fetchAmazonProductByCategory(categoryId: String): DataOrException<AmazonProductByCategoryQuery.Data, ApolloException,Boolean> {
        val clientApollo =  client.apollo.query(AmazonProductByCategoryQuery(categoryId = categoryId)).execute()

        if(clientApollo.exception != null){
            return DataOrException(null,clientApollo.exception!!,false)
        }

        return DataOrException(clientApollo.data!!,null,false)
    }


}