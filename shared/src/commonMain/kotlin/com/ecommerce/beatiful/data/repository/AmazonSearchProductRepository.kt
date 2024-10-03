package com.ecommerce.beatiful.data.repository

import com.apollographql.apollo.exception.ApolloException
import com.ecommerce.beatiful.data.client.AmazonProductClient
import com.ecommerce.beatiful.data.local.AmazonProductSearchResource
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.model.toAmazonProductResult
import com.ecommerce.beatiful.util.DataOrException
import com.ecommerce.beatiful.util.Helpers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AmazonSearchProductRepository : KoinComponent {
    private val client: AmazonProductClient by inject()
    private val resource: AmazonProductSearchResource by inject()

    suspend fun fetchAmazonResult(
        product: String,
        differenceMinutes: Int
    ): DataOrException<List<AmazonResultSerialization>, ApolloException,Boolean> {
        val helper = Helpers()
        val amazonData = resource.getAmazonProductSearchData()

        if (amazonData == null) {
            val response = client.fetchAmazonSearchProduct(product)
            val data = response.data
            if (data != null) {
                resource.insertAmazonProductSearchData(data)

                return    DataOrException(data.amazonProductSearchResults!!.productResults!!.results!!.map { it!!.toAmazonProductResult() }, null, false)
            }
            return DataOrException(null, response.exception!!, false)
        }

        if (helper.shouldUpdateOrNoDatabase(
                timeStamp = amazonData!!.createAt,
                differenceMinutes = differenceMinutes
            )
        ) {
            val response = client.fetchAmazonSearchProduct(product)
            val data = response.data
            //uma maneira de nao precisa comparar se possui internet
            //caso der null no response e porque possivelmente nao conseguiu conexao por falha de internet
            //ja que possuii data no local eu retorno o local
            if (data  != null) {
                resource.deleteAmazonProductSearchData()
                resource.insertAmazonProductSearchData(data)
                return  DataOrException(data.amazonProductSearchResults!!.productResults!!.results!!.map { it!!.toAmazonProductResult() }, null, false)
            }
            return DataOrException(amazonData.results, null, false)
        }

        return DataOrException(amazonData.results, null, false)


    }

}