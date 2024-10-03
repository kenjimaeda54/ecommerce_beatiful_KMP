package com.ecommerce.beatiful.android.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ecommerce.beatiful.R
import com.ecommerce.beatiful.android.ui.screens.home.HomeScreen
import com.ecommerce.beatiful.android.util.BottomBarScreen
import com.ecommerce.beatiful.android.util.BottomScreens
import com.ecommerce.beatiful.android.util.ComposableLifecycle
import com.ecommerce.beatiful.android.util.StackScreens
import com.ecommerce.beatiful.android.util.categoryMap
import com.ecommerce.beatiful.viewModel.AmazonProductCategoryViewModel


@Composable
fun SplashScreen(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.ecommerce.beatiful.android.R.raw.splash))
    val logoAnimationState =
        animateLottieCompositionAsState(composition = composition)
    val progress by animateLottieCompositionAsState(composition = composition)



    if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
        navController.navigate(BottomBarScreen.Home.route) {
            popUpTo(StackScreens.SplashScreen.name) {
                inclusive = true
            }
        }
    } else {
        LottieAnimation(composition = composition, progress = { progress })
    }
}