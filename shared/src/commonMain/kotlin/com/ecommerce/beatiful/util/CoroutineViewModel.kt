package com.ecommerce.beatiful.util

import kotlinx.coroutines.CoroutineScope



expect open  class CoroutineViewModel() {

    val scope: CoroutineScope

}