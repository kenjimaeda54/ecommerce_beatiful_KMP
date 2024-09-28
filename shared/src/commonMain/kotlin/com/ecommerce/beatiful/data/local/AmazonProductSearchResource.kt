package com.ecommerce.beatiful.data.local

import com.ecommerce.beatiful.AmazonProductQuery
import com.ecommerce.beatiful.data.model.AmazonProductDataSerialization
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.model.toAmazonProductResult
import com.ecommerce.beatiful.db.EcommerceDB
import kotlinx.datetime.Clock
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

private val json =  Json{
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true

}






class AmazonProductSearchResource(private val dataBase: EcommerceDB) {

    fun getAmazonProductSearchData(): AmazonProductDataSerialization? {
        val amazonProductData = dataBase.amazonSearchProductDataQueries
            .getAmazonProductData().executeAsOneOrNull() ?: return null
        val amazonProduct = AmazonProductDataSerialization(
            createAt =  amazonProductData!!.createdAt,
            id= amazonProductData!!.id,
            results = json.decodeFromString(ListSerializer(AmazonResultSerialization.serializer()), amazonProductData!!.resultAmazon!!)
        )
        return amazonProduct
    }


    fun insertAmazonProductSearchData(data: AmazonProductQuery.Data) {
           val amazonResultSerialization = data.amazonProductSearchResults!!.productResults!!.results!!.map {
                it!!.toAmazonProductResult()
            }
            dataBase.amazonSearchProductDataQueries.insertAmazonProduct(
                createdAt = Clock.System.now().toEpochMilliseconds(),
                resultAmazon = json.encodeToString(ListSerializer(AmazonResultSerialization.serializer()), amazonResultSerialization),
            )

    }

    fun deleteAmazonProductSearchData() {
        dataBase.amazonSearchProductDataQueries.deleteAmazonProductData()
    }
}