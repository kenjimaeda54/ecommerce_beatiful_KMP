package com.ecommerce.beatiful

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform