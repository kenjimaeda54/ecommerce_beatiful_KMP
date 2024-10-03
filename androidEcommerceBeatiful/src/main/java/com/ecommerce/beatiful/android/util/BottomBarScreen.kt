package com.ecommerce.beatiful.android.util
import com.ecommerce.beatiful.android.*

sealed class BottomBarScreen(
    val route: String,
    val iconSelected: Int,
    val iconSelectedNotSelected: Int
) {

    data object Home : BottomBarScreen(
        route = "home",
        iconSelected = R.drawable.home,
        iconSelectedNotSelected = R.drawable.home_empty
    )

    data object Catalog : BottomBarScreen(
        route = "catalog",
        iconSelected = R.drawable.catalog,
        iconSelectedNotSelected = R.drawable.search_empty

    )

    data object Cart : BottomBarScreen(
        route = "cart",
        iconSelected = R.drawable.cart,
        iconSelectedNotSelected = R.drawable.cart_empty
    )

    data object Favorites : BottomBarScreen(
        route = "favorite",
        iconSelected = R.drawable.favorite,
        iconSelectedNotSelected = R.drawable.favorite_empty
    )

    data object Profile : BottomBarScreen(
        route = "profile",
        iconSelected = R.drawable.profile,
        iconSelectedNotSelected = R.drawable.profile_empty
    )

}