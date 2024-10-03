package com.ecommerce.beatiful.android.util

class  BottomScreens {
    companion object {
        fun screens(): List<BottomBarScreen> {
            return listOf(
                BottomBarScreen.Home,
                BottomBarScreen.Cart,
                BottomBarScreen.Catalog,
                BottomBarScreen.Favorites,
                BottomBarScreen.Profile,
            )
        }

    }
}