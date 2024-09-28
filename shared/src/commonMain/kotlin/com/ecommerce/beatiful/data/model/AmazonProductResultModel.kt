package com.ecommerce.beatiful.data.model

import com.ecommerce.beatiful.AmazonProductQuery
import kotlinx.serialization.Serializable


data class  AmazonProductDataSerialization(
    val id: Long,
    val createAt: Long,
    val results: List<AmazonResultSerialization>

)

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

fun AmazonResultSerialization.toAmazonProductResult(): AmazonProductQuery.Result {
    return AmazonProductQuery.Result(
        asin = asin,
        imageUrls = imageUrls as List<String>,
        price = AmazonProductQuery.Price(display = price),
        title = title,
        brand = brand,
        url = url,
        rating = rating,
        seller = AmazonProductQuery.Seller(
            name = seller?.name,
            logoUrl = seller?.logoUrl,
            rating = seller?.rating
        )
    )
}

fun AmazonProductQuery.Result.toAmazonProductResult(): AmazonResultSerialization {
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


