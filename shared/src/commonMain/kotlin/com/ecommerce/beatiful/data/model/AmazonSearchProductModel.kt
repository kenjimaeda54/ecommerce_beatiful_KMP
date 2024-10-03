package com.ecommerce.beatiful.data.model

import com.ecommerce.beatiful.AmazonProductSearchQuery
import kotlinx.serialization.Serializable


data class  AmazonSearchProductModel(
    val id: Long,
    val createAt: Long,
    val results: List<AmazonResultSerialization>

)


