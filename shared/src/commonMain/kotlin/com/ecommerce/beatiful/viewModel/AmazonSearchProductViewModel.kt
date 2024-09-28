package com.ecommerce.beatiful.viewModel

import com.apollographql.apollo.exception.ApolloException
import com.ecommerce.beatiful.AmazonProductQuery
import com.ecommerce.beatiful.data.client.AmazonProductClient
import com.ecommerce.beatiful.data.client.ApolloImplementation
import com.ecommerce.beatiful.data.model.AmazonResultSerialization
import com.ecommerce.beatiful.data.repository.AmazonSearchProductRepository
import com.ecommerce.beatiful.util.CoroutineViewModel
import com.ecommerce.beatiful.util.DataOrException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AmazonSearchProductViewModel: CoroutineViewModel(), KoinComponent {
    private val amazonSearchProductRepository: AmazonSearchProductRepository by inject()
    private  var _amazonSearchProduct = MutableStateFlow< DataOrException<List<AmazonResultSerialization>,ApolloException, Boolean>>(DataOrException(null,null, false))
    val  amazonSearchProduct: StateFlow<DataOrException<List<AmazonResultSerialization>,ApolloException, Boolean>> = _amazonSearchProduct

    fun fetchAmazonResult(product: String,differenceMinutes: Int) {
        scope.launch {
            _amazonSearchProduct.value = DataOrException(null,null, true)
            _amazonSearchProduct.value = amazonSearchProductRepository.fetchAmazonResult(product,differenceMinutes)
        }

   }
}