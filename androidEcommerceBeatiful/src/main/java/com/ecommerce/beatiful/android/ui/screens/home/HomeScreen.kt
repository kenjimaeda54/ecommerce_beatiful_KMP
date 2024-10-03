package com.ecommerce.beatiful.android.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommerce.beatiful.android.ui.theme.fontsOpenSans
import com.ecommerce.beatiful.android.util.ComposableLifecycle
import com.ecommerce.beatiful.android.util.categoryMap
import com.ecommerce.beatiful.viewModel.AmazonProductCategoryViewModel
import com.ecommerce.beatiful.viewModel.AmazonSearchProductViewModel


@Composable
fun HomeScreen() {
    val viewModel = viewModel<AmazonProductCategoryViewModel>()
    val allProductsList by viewModel.allProductsByCategory.collectAsState()

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.getAllProductsByCategory(differenceMinutes = 15)
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.primary
    ) {
        Column {
            if (allProductsList.isLoading) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                    ) {
                    Text(
                        text = "loading",
                        fontFamily = fontsOpenSans,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                }

            } else {
                allProductsList.data!!.forEach {
                    Text(categoryMap[it.id] ?: "",
                        fontFamily = fontsOpenSans,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                        )
                    LazyRow {
                        items(it.results!!.size) { index ->
                            Text(
                                text = it.results!![index].title,
                                fontFamily = fontsOpenSans,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }
        }

    }


}