package com.ecommerce.beatiful.data.model

import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.adapter.AmazonProductByCategoryQuery_ResponseAdapter
import com.ecommerce.beatiful.db.AmazonProductCategory
import kotlinx.datetime.Clock

data class AmazonProductCategoryModel(
    val id: String,
    val createAt: Long,
    val name: String?,
    val breadcrumbPath: String?,
    var results: List<AmazonResultSerialization>?
)


fun AmazonProductCategory.toAmazonProductCategoryModel(): AmazonProductCategoryModel {
    return AmazonProductCategoryModel(
        id = id,
        createAt = createdAt,
        name = name,
        breadcrumbPath = breadcrumbPath,
        results = resultAmazon as List<AmazonResultSerialization>
    )
}

fun AmazonProductByCategoryQuery.Data.toAmazonProductCategory(): AmazonProductCategoryModel {
    return AmazonProductCategoryModel(
        id = amazonProductCategory!!.id!!,
        createAt = Clock.System.now().toEpochMilliseconds(),
        name = amazonProductCategory!!.name,
        breadcrumbPath = amazonProductCategory!!.breadcrumbPath,
        results = amazonProductCategory!!.productResults!!.results!!.map { it!!.toAmazonProductResult() }
    )
}