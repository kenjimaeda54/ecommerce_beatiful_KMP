package com.ecommerce.beatiful.viewModel

import com.ecommerce.beatiful.AmazonProductQuery
import com.ecommerce.beatiful.data.client.AmazonProductClient
import com.ecommerce.beatiful.data.client.ApolloImplementation
import com.ecommerce.beatiful.data.repository.AmazonSearchProductRepository
import com.ecommerce.beatiful.util.CoroutineViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AmazonSearchProductViewModel: CoroutineViewModel(), KoinComponent {
    private val amazonSearchProductRepository: AmazonSearchProductRepository by inject()
    private  var _amazonSearchProduct = MutableStateFlow<AmazonProductQuery.Data?>(null)
    val  amazonSearchProduct: StateFlow<AmazonProductQuery.Data?> = _amazonSearchProduct

    fun fetchAmazonResult(product: String,differenceMinutes: Int) {
        scope.launch {
            _amazonSearchProduct.value = amazonSearchProductRepository.fetchAmazonResult(product,differenceMinutes)
        }

   }
}