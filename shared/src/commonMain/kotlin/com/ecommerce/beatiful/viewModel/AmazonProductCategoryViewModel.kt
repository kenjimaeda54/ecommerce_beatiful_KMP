package com.ecommerce.beatiful.viewModel

import com.apollographql.apollo.exception.ApolloException
import com.ecommerce.beatiful.data.model.AmazonProductCategoryModel
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.repository.AmazonProductByCategoryRepository
import com.ecommerce.beatiful.util.CoroutineViewModel
import com.ecommerce.beatiful.util.DataOrException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AmazonProductCategoryViewModel : CoroutineViewModel(), KoinComponent {
    private val repository: AmazonProductByCategoryRepository by inject()
    private val _productByCategory =
        MutableStateFlow<DataOrException<AmazonProductCategoryModel, ApolloException, Boolean>>(
            DataOrException(null, null, false)
        )
    val productByCategory: StateFlow<DataOrException<AmazonProductCategoryModel, ApolloException, Boolean>> =
        _productByCategory
    private val _allProductsByCategory =
        MutableStateFlow<DataOrException<List<AmazonProductCategoryModel>, ApolloException, Boolean>>(
            DataOrException(null, null, true)
        )
    val allProductsByCategory: StateFlow<DataOrException<List<AmazonProductCategoryModel>, ApolloException, Boolean>> =
        _allProductsByCategory


    fun getProductByCategory(categoryId: String, differenceMinutes: Int) {
        scope.launch {
            _productByCategory.value = DataOrException(null, null, true)
            _productByCategory.value = repository.fetchAmazonProductByCategory(
                categoryId = categoryId,
                differenceMinutes = differenceMinutes
            )
        }
    }

    fun getAllProductsByCategory(differenceMinutes: Int) {
        scope.launch {

            _allProductsByCategory.value = DataOrException(null, null, true)
            val listIds = listOf(
                "360832011",
                "15342831",
                "16227130011",
                "172282",
                "3760901",
            )

            for (id in listIds) {
                val response =
                    repository.fetchAmazonProductByCategory(categoryId = id, differenceMinutes = differenceMinutes)
                if (response.data != null) {
                    _allProductsByCategory.update {
                        //nao usar return@launch porque ira finalizar o for
                        if (_allProductsByCategory.value.data == null) {
                            DataOrException(listOf(response.data), null, true)
                        } else {
                            val currentProductList =
                                _allProductsByCategory.value.data!!.plus(response.data)

                            DataOrException(currentProductList, null, true)
                        }

                    }

                }
            }
            _allProductsByCategory.value = DataOrException(_allProductsByCategory.value.data, null, false)
        }
    }

}