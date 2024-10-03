package com.ecommerce.beatiful.data.model

import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.AmazonProductSearchQuery
import kotlinx.serialization.Serializable



@Serializable
data class  AmazonResultSerialization(
    val asin: String? = null,
    val imageUrls: List<String?>? = listOf(),
    val price: String,
    val title: String,
    val brand: String? = null,
    val url: String,
    val rating: Double,
    val seller: AmazonSellerSerialization? = null

)

@Serializable
data class AmazonSellerSerialization(
    val name: String?,
    val logoUrl: String?,
    val rating: Double?
)

fun AmazonProductByCategoryQuery.Result.toAmazonProductResult(): AmazonResultSerialization {
    return AmazonResultSerialization(
        asin = asin,
        imageUrls = if(imageUrls == null) listOf() else imageUrls as List<String>,
        price = price?.display ?: "",
        title = title,
        brand = brand,
        url = url.toString(),
        rating = rating ?: 0.0,
        seller = AmazonSellerSerialization(
            name = seller?.name,
            logoUrl = seller?.logoUrl?.toString(),
            rating = seller?.rating
        )
    )
}


fun AmazonProductSearchQuery.Result.toAmazonProductResult(): AmazonResultSerialization {
    return AmazonResultSerialization(
        asin = asin,
        imageUrls = imageUrls as List<String>,
        price = price?.display ?: "",
        title = title,
        brand = brand,
        url = url.toString(),
        rating = rating ?: 0.0,
        seller = AmazonSellerSerialization(
            name = seller?.name,
            logoUrl = seller?.logoUrl?.toString(),
            rating = seller?.rating
        )
    )
}

