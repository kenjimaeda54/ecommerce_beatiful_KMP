package com.ecommerce.beatiful.data.local

import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.data.model.AmazonProductCategoryModel
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.model.toAmazonProductCategoryModel
import com.ecommerce.beatiful.data.model.toAmazonProductResult
import com.ecommerce.beatiful.db.EcommerceDB
import com.ecommerce.beatiful.util.Helpers
import kotlinx.datetime.Clock
import kotlinx.serialization.builtins.ListSerializer



class AmazonProductByCategoryResource(private val dataBase: EcommerceDB) {
   private val helper = Helpers()

    fun getAmazonProductByCategory(categoryId: String): AmazonProductCategoryModel?  {
        val amazonProduct = dataBase.amazonProductCategoryQueries
            .getAmazonProductByCategory(id = categoryId).executeAsOneOrNull() ?: return null

        val amazonProductModel = AmazonProductCategoryModel(
            id = amazonProduct.id,
            createAt = amazonProduct.createdAt,
            results = helper.json.decodeFromString(ListSerializer(AmazonResultSerialization.serializer()), amazonProduct.resultAmazon!!),
            name = amazonProduct.name,
            breadcrumbPath = amazonProduct.breadcrumbPath
        )

        return amazonProductModel
    }

    fun insertAmazonProductByCategory(data: AmazonProductByCategoryQuery.Data) {
        val amazonResultSerialization =  data!!.amazonProductCategory!!.productResults!!.results!!.map {
            it!!.toAmazonProductResult()
        }
        dataBase.amazonProductCategoryQueries.insertAmazonProductCategory(
            id = data.amazonProductCategory!!.id!!,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            resultAmazon = helper.json.encodeToString(ListSerializer(AmazonResultSerialization.serializer()), amazonResultSerialization),
            name = data.amazonProductCategory!!.name,
            breadcrumbPath = data.amazonProductCategory!!.breadcrumbPath
        )
    }

    fun deleteAmazonProductByCategory(categoryId: String) {
        dataBase.amazonProductCategoryQueries.deleteAmazonPRoductByCategoryById(id = categoryId)
    }
}