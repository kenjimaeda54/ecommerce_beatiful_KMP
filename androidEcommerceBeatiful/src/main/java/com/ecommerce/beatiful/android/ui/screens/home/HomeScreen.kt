package com.ecommerce.beatiful.android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommerce.beatiful.android.ui.screens.home.view.CustomTextField
import com.ecommerce.beatiful.android.ui.screens.home.view.RowCategoryMap
import com.ecommerce.beatiful.android.ui.theme.EcommerceTheme
import com.ecommerce.beatiful.android.ui.theme.fontsOpenSans
import com.ecommerce.beatiful.android.util.categoryMap
import com.ecommerce.beatiful.viewModel.AmazonProductCategoryViewModel


@Composable
fun HomeScreen() {
    val viewModel = viewModel<AmazonProductCategoryViewModel>()
    val allProductsList by viewModel.listProductsCategory.collectAsState()

    Surface(
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondary.copy(0.3f),
                        shape = RoundedCornerShape(
                            bottomEnd = 10.dp,
                            bottomStart = 10.dp
                        )
                    )
                    .padding(
                        horizontal = 13.dp,
                        vertical = 15.dp
                    )

            ) {
                CustomTextField(placeHolder = "Procurar na loja toda",
                    value = "", onValueChange = {})

            }
            Column(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondary.copy(0.3f),
                        shape = RoundedCornerShape(
                            topEnd = 15.dp,
                            topStart = 15.dp
                        )
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(
                        top = 15.dp,
                        bottom = 80.dp
                    ),

            ) {

                categoryMap.entries.forEach { (id, _) ->
                    viewModel.getProductByCategory(id, differenceMinutes = 1)
                    val findProduct = allProductsList.find { it.id == id }
                    Text(
                        categoryMap[id] ?: "",
                        modifier = Modifier.padding(start = 10.dp, top = 25.dp),
                        fontFamily = fontsOpenSans,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary.copy(0.7f)
                    )

                    if (findProduct?.results?.isEmpty() == true) {
                        Text(
                            text = "Loading",
                            fontFamily = fontsOpenSans,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        LazyRow(
                           horizontalArrangement = Arrangement.spacedBy(15.dp),
                            contentPadding =  PaddingValues(start = 10.dp)
                        ) {
                            items(findProduct?.results!!.size) { index ->
                                RowCategoryMap(item = findProduct.results!![index])
                            }
                        }
                    }
                }
            }
        }


    }


}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}