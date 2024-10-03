package com.ecommerce.beatiful.data.local

import com.ecommerce.beatiful.AmazonProductSearchQuery
import com.ecommerce.beatiful.data.model.AmazonSearchProductModel
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.model.toAmazonProductResult
import com.ecommerce.beatiful.db.EcommerceDB
import com.ecommerce.beatiful.util.Helpers
import kotlinx.datetime.Clock
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json



class AmazonProductSearchResource(private val dataBase: EcommerceDB) {
    private val helper = Helpers()

    fun getAmazonProductSearchData(): AmazonSearchProductModel? {
        val amazonProductData = dataBase.amazonSearchProductDataQueries
            .getAmazonProductData().executeAsOneOrNull() ?: return null
        val amazonProduct = AmazonSearchProductModel(
            createAt =  amazonProductData!!.createdAt,
            id= amazonProductData!!.id,
            results = helper.json.decodeFromString(ListSerializer(AmazonResultSerialization.serializer()), amazonProductData!!.resultAmazon!!)
        )
        return amazonProduct
    }


    fun insertAmazonProductSearchData(data: AmazonProductSearchQuery.Data) {
           val amazonResultSerialization = data.amazonProductSearchResults!!.productResults!!.results!!.map {
                it!!.toAmazonProductResult()
            }
            dataBase.amazonSearchProductDataQueries.insertAmazonProduct(
                createdAt = Clock.System.now().toEpochMilliseconds(),
                resultAmazon = helper.json.encodeToString(ListSerializer(AmazonResultSerialization.serializer()), amazonResultSerialization),
            )

    }

    fun deleteAmazonProductSearchData() {
        dataBase.amazonSearchProductDataQueries.deleteAmazonProductData()
    }
}