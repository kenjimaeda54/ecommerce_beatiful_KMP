package com.ecommerce.beatiful.android.util

enum class StackScreens{
    SplashScreen;

    companion object {
        fun fromRoutes(route: String): StackScreens = when(route.substringBefore("/")) {
           SplashScreen.name -> SplashScreen
           else -> throw IllegalArgumentException("Invalid route $route")
        }
    }
}