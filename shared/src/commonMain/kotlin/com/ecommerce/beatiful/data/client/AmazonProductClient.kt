package com.ecommerce.beatiful.data.client

import com.apollographql.apollo.exception.ApolloException
import com.ecommerce.beatiful.AmazonProductQuery
import com.ecommerce.beatiful.util.DataOrException

class AmazonProductClient(private  val client: IApolloClient) {

    suspend fun fetchAmazonResult(product: String): DataOrException<AmazonProductQuery.Data, ApolloException,Boolean> {
       val clientApollo =  client.apollo.query(AmazonProductQuery(searchTerm = product)).execute()
        if(clientApollo.exception != null){
            return DataOrException(null,clientApollo.exception!!,false)
        }
       return DataOrException(clientApollo.data!!,null,false)

    }
}