package com.ecommerce.beatiful.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel


actual open class CoroutineViewModel {

    actual val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun  clear() {
        scope.cancel()
    }


}