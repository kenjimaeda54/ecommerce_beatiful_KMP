package com.ecommerce.beatiful.android.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommerce.beatiful.android.util.ComposableLifecycle
import com.ecommerce.beatiful.viewModel.AmazonSearchProductViewModel


@Composable
fun HomeScreen() {
    val viewModel = viewModel<AmazonSearchProductViewModel>()
    val amazonSearchProduct by viewModel.amazonSearchProduct.collectAsState()


    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.fetchAmazonResult(product = "phone", differenceMinutes = 1)
        }

    }

    if (amazonSearchProduct.isLoading) {
        Text(text = "Loading...")
    }else {
        amazonSearchProduct.data?.get(0)?.let { Text(text = it.title) }
    }


}