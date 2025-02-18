package com.ecommerce.beatiful.mocks

import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.AmazonProductSearchQuery
import com.ecommerce.beatiful.data.client.contracts.AmazonProduct
import com.ecommerce.beatiful.util.DataOrException
import com.ecommerce.beatiful.util.IdsProductsCategory


class FakeAmazonProductImplementation : AmazonProduct {
    private var shouldReturnError = false
    private var shouldReturnEmptyProducts = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun setReturnDataWithProductsEmpty(value: Boolean) {
        shouldReturnEmptyProducts = value
    }

    override suspend fun fetchAmazonSearchProduct(product: String): DataOrException<AmazonProductSearchQuery.Data, String, Boolean> {
        if (shouldReturnError) {
            return DataOrException(
                data = null,
                exception = "Error get data",
                isLoading = false
            )
        } else {
            val data = if (shouldReturnEmptyProducts) {
                AmazonProductSearchQuery.Data(
                    amazonProductSearchResults = AmazonProductSearchQuery.AmazonProductSearchResults(
                        productResults = AmazonProductSearchQuery.ProductResults(
                            results = listOf())))
            } else {
                AmazonProductSearchQuery.Data(
                    amazonProductSearchResults = AmazonProductSearchQuery.AmazonProductSearchResults(
                        productResults = AmazonProductSearchQuery.ProductResults(
                            results = listOf(
                                AmazonProductSearchQuery.Result(
                                    asin = "",
                                    brand = "Lacoste",
                                    imageUrls = listOf(
                                        "https://github.com/kenjimaead5.png"
                                    ),
                                    price = AmazonProductSearchQuery.Price(
                                        display = "R$ 150.34"
                                    ),
                                    rating = 4.35,
                                    seller = AmazonProductSearchQuery.Seller(
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

                )
            }
            return DataOrException(data = data, exception = null, isLoading = false)
        }
    }

    override suspend fun fetchAmazonProductByCategory(categoryId: String): DataOrException<AmazonProductByCategoryQuery.Data, String, Boolean> {
        return if (shouldReturnError) {
            DataOrException(
                data = null,
                exception = "Error get data",
                isLoading = false
            )
        } else {
            val data = if (shouldReturnEmptyProducts) {
                AmazonProductByCategoryQuery.Data(
                    amazonProductCategory = AmazonProductByCategoryQuery.AmazonProductCategory(
                        name = "Mochilas em geral",
                        breadcrumbPath = BREADCRUMB_PATH,
                        id = IdsProductsCategory.BACKPACK.value,
                        productResults = AmazonProductByCategoryQuery.ProductResults(
                            results = listOf())))
            } else {
                AmazonProductByCategoryQuery.Data(
                    amazonProductCategory = AmazonProductByCategoryQuery.AmazonProductCategory(
                        name = "Mochilas em geral",
                        breadcrumbPath = BREADCRUMB_PATH,
                        id = IdsProductsCategory.BACKPACK.value,
                        productResults = AmazonProductByCategoryQuery.ProductResults(
                            results = listOf(
                                AmazonProductByCategoryQuery.Result(
                                    asin = "",
                                    brand = "Lacoste",
                                    imageUrls = listOf(
                                        "https://github.com/kenjimaead5.png"
                                    ),
                                    price = AmazonProductByCategoryQuery.Price(
                                        display = "R$ 150.34"
                                    ),
                                    rating = 4.35,
                                    seller = AmazonProductByCategoryQuery.Seller(
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

                )
            }
            val returnDataConformId = if (data.amazonProductCategory?.id == categoryId) {
                data
            } else AmazonProductByCategoryQuery.Data(
                amazonProductCategory = AmazonProductByCategoryQuery.AmazonProductCategory(
                    name = "Mochilas em geral",
                    breadcrumbPath = BREADCRUMB_PATH,
                    id = IdsProductsCategory.BACKPACK.value,
                    productResults = AmazonProductByCategoryQuery.ProductResults(
                        results = listOf()
                    )
                )
            )
            return DataOrException(data = returnDataConformId, exception = null, isLoading = false)
        }
    }

    companion object {
        const val BREADCRUMB_PATH = "Mochilas"
    }

}
