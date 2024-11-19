package mocks

import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.data.local.contracts.AmazonProductByCategoryResource
import com.ecommerce.beatiful.data.model.AmazonProductCategoryModel
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.model.AmazonSellerSerialization
import com.ecommerce.beatiful.data.model.toAmazonProductResult
import kotlinx.datetime.Clock
import utils.IdsProductsCategory

class FakeAmazonProductCategoryResource : AmazonProductByCategoryResource {
    private var haveData = false
    private val currentTime = Clock.System.now().toEpochMilliseconds()
    private var dataDatabase = mutableListOf(
        AmazonProductCategoryModel(
            name = "Mochilas em geral",
            breadcrumbPath = BREADCRUMB_PATH_FROM_DATABASE_BACKPACKS,
            id = IdsProductsCategory.BACKPACK.value,
            createAt = currentTime,
            results = mutableListOf(
                AmazonResultSerialization(
                    asin = "",
                    brand = "Lacoste",
                    imageUrls = listOf(
                        "https://github.com/kenjimaead5.png"
                    ),
                    price =
                    "R$ 150.34",
                    rating = 4.35,
                    seller = AmazonSellerSerialization(
                        logoUrl = "https://github.com/kenjimaead5.png",
                        name = "Lacoste",
                        rating = 4.96
                    ),
                    title = "Mochila linda para estudos",
                    url = "https://github.com/kenjimaead5.png"
                )
            )
        )
    )

    fun setHaveData(value: Boolean) {
        haveData = value
    }

    override fun getAmazonProductByCategory(categoryId: String): AmazonProductCategoryModel? {
        return if (haveData) {
            dataDatabase.first { it.id == categoryId }
        } else {
            null
        }
    }

    override fun insertAmazonProductByCategory(data: AmazonProductByCategoryQuery.Data) {
        val amazonResultSerialization =
            data!!.amazonProductCategory!!.productResults!!.results!!.map {
                it!!.toAmazonProductResult()
            }
        val amazonProductCategoryModel = AmazonProductCategoryModel(
            id = data.amazonProductCategory!!.id!!,
            name = data.amazonProductCategory!!.name!!,
            breadcrumbPath = data.amazonProductCategory!!.breadcrumbPath!!,
            createAt = currentTime,
            results = amazonResultSerialization
        )
        dataDatabase.add(amazonProductCategoryModel)
    }

    override fun deleteAmazonProductByCategory(categoryId: String) {
         dataDatabase = dataDatabase.map {
             if (it.id == categoryId) {
                 AmazonProductCategoryModel(
                     breadcrumbPath = it.breadcrumbPath,
                     createAt = it.createAt,
                     id = it.id,
                     name = it.name,
                     results = listOf()
                 )
             }else {
                 it
             }
         }.toMutableList()
    }

    companion object {
        const val BREADCRUMB_PATH_FROM_DATABASE_BACKPACKS = "Mochila escolar"
        const val BREADCRUMB_PATH_FROM_DATABASE_CLEANING = "Utensilios em geral"
    }
}