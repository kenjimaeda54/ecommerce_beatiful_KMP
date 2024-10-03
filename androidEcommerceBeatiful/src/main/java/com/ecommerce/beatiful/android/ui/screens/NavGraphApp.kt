package com.ecommerce.beatiful.android.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ecommerce.beatiful.android.ui.screens.cart.CartScreen
import com.ecommerce.beatiful.android.ui.screens.catalog.CatalogScreen
import com.ecommerce.beatiful.android.ui.screens.favorites.FavoritesScreen
import com.ecommerce.beatiful.android.ui.screens.home.HomeScreen
import com.ecommerce.beatiful.android.ui.screens.profile.ProfileScreen
import com.ecommerce.beatiful.android.ui.screens.splash.SplashScreen
import com.ecommerce.beatiful.android.util.BottomBarScreen
import com.ecommerce.beatiful.android.util.BottomScreens
import com.ecommerce.beatiful.android.util.StackScreens


@Composable
fun  NavGraphApp(navController: NavHostController) {

    NavHost(navController = navController, startDestination = StackScreens.SplashScreen.name) {
        composable(StackScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(BottomBarScreen.Catalog.route) {
            CatalogScreen()
        }
        composable(BottomBarScreen.Cart.route){
            CartScreen()
        }
        composable(BottomBarScreen.Favorites.route){
            FavoritesScreen()
        }
        composable(BottomBarScreen.Profile.route){
            ProfileScreen()
        }
    }
}