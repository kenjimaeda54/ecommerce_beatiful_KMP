package com.ecommerce.beatiful.viewModel

import com.ecommerce.beatiful.data.model.AmazonProductCategoryModel
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.repository.AmazonProductByCategoryRepository
import com.ecommerce.beatiful.data.repository.AmazonSearchProductRepository
import com.ecommerce.beatiful.util.CoroutineViewModel
import com.ecommerce.beatiful.util.DataOrException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel: CoroutineViewModel(), KoinComponent {
    private val amazonSearchProductRepository: AmazonSearchProductRepository by inject()
    private var _amazonSearchProduct =
        MutableStateFlow<DataOrException<List<AmazonResultSerialization>, String, Boolean>>(
            DataOrException(null, null, false)
        )
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
                _listProductsCategory.value = _listProductsCategory.value.map {
                    if (it.id == categoryId) {
                        it.copy(
                            createAt = response.data.createAt,
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

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun fetchAmazonResult(product: StateFlow<String>, differenceMinutes: Int) {
        scope.launch {
            _amazonSearchProduct.value = DataOrException(null, null, true)
            val flowDatOrExceptionAmazonResult = product.debounce(300).flatMapLatest { query ->
                handleSearchProduct(query, 3)
            }
            _amazonSearchProduct.value = flowDatOrExceptionAmazonResult.first()
        }

    }

    private fun handleSearchProduct(
        query: String,
        differenceMinutes: Int
    ): Flow<DataOrException<List<AmazonResultSerialization>, String, Boolean>> {
        return flow<DataOrException<List<AmazonResultSerialization>, String, Boolean>> {
            val result = amazonSearchProductRepository.fetchAmazonResult(query, differenceMinutes)
            emit(result)
        }
    }
}