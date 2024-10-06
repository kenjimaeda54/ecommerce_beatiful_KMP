package com.ecommerce.beatiful.viewModel

import com.ecommerce.beatiful.data.model.AmazonProductCategoryModel
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.repository.AmazonProductByCategoryRepository
import com.ecommerce.beatiful.util.CoroutineViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AmazonProductCategoryViewModel : CoroutineViewModel(), KoinComponent {
    private val repository: AmazonProductByCategoryRepository by inject()
    private val _listProductsCategory =
        MutableStateFlow<List<AmazonProductCategoryModel>>(
            listOf(
                AmazonProductCategoryModel(
                    id = "360832011",
                    createAt = 0,
                    name = "Mochilas",
                    breadcrumbPath = "",
                    results = mutableListOf<AmazonResultSerialization>()
                ),
                AmazonProductCategoryModel(
                    id = "15342831",
                    createAt = 0,
                    name = "Limpeza",
                    breadcrumbPath = "",
                    results = mutableListOf<AmazonResultSerialization>()
                ),
                AmazonProductCategoryModel(
                    id = "16227130011",
                    createAt = 0,
                    name = "Video Games",
                    breadcrumbPath = "",
                    results = mutableListOf<AmazonResultSerialization>()
                ),
                AmazonProductCategoryModel(
                    id = "172282",
                    createAt = 0,
                    name = "Eletronicos",
                    breadcrumbPath = "",
                    results = mutableListOf<AmazonResultSerialization>()
                ),
                AmazonProductCategoryModel(
                    id = "3760901",
                    createAt = 0,
                    name = "Saude",
                    breadcrumbPath = "",
                    results = mutableListOf<AmazonResultSerialization>()
                )
            )
        )
    val listProductsCategory: StateFlow<List<AmazonProductCategoryModel>> =
        _listProductsCategory


    fun getProductByCategory(categoryId: String, differenceMinutes: Int) {
        scope.launch {
            val response = repository.fetchAmazonProductByCategory(
                categoryId = categoryId,
                differenceMinutes = differenceMinutes
            )
            if (response.data != null) {

                _listProductsCategory.update {
                    _listProductsCategory.value.map {
                        if (it.id == categoryId) {
                            it.copy(
                                createAt = response.data!!.createAt,
                                breadcrumbPath = response.data.breadcrumbPath,
                                results = response.data.results
                            )
                        } else {
                            it
                        }
                    }
                }

            }

        }

    }
}
