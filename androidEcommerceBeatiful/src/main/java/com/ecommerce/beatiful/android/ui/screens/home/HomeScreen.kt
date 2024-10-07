package com.ecommerce.beatiful.android.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.ecommerce.beatiful.android.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommerce.beatiful.R
import com.ecommerce.beatiful.android.ui.screens.home.view.CustomTextField
import com.ecommerce.beatiful.android.ui.screens.home.view.RowCategoryMap
import com.ecommerce.beatiful.android.ui.theme.EcommerceTheme
import com.ecommerce.beatiful.android.ui.theme.fontsOpenSans
import com.ecommerce.beatiful.android.util.categoryMap
import com.ecommerce.beatiful.viewModel.AmazonProductCategoryViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeScreen() {
    val viewModel = viewModel<AmazonProductCategoryViewModel>()
    val allProductsList by viewModel.listProductsCategory.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val stateLazy = rememberLazyListState()
    var product by remember {
        mutableStateOf("")
    }

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
                    value = product, onValueChange = {
                        product = it
                    })

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
                    .padding(
                        top = 15.dp,
                        bottom = 80.dp
                    ),

                ) {

                Text(
                    text = "Categorias",
                    modifier = Modifier.padding(start = 10.dp),
                    fontFamily = fontsOpenSans,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary.copy(0.7f)
                )
                Row(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    categoryMap.entries.forEachIndexed { index, (_, key) ->
                        Column(
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    stateLazy.animateScrollToItem(index)
                                }

                            },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center

                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(
                                        all = 8.dp
                                    )
                                    .size(40.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                when(categoryMap.values.toList()[index]){
                                    "Mochilas" -> Icon(
                                        modifier = Modifier.size(30.dp),
                                        painter = painterResource(id = com.ecommerce.beatiful.android.R.drawable.backpaback),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                    "Limpeza" -> Icon(
                                        modifier = Modifier.size(30.dp),
                                        painter = painterResource(id = com.ecommerce.beatiful.android.R.drawable.clean),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                    "Video Games" -> Icon(
                                        modifier = Modifier.size(30.dp),
                                        painter = painterResource(id = com.ecommerce.beatiful.android.R.drawable.game),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary                                    )
                                    "Eletronicos" -> Icon(
                                        modifier = Modifier.size(30.dp),
                                        painter = painterResource(id = com.ecommerce.beatiful.android.R.drawable.eletronic),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                    else -> Icon(
                                        modifier = Modifier.size(30.dp),
                                        painter = painterResource(id = com.ecommerce.beatiful.android.R.drawable.health),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                            }

                            Text(
                                text = key,
                                fontFamily = fontsOpenSans,
                                color = MaterialTheme.colorScheme.tertiary.copy(0.7f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )

                        }
                    }
                }

               LazyColumn(
                   state = stateLazy
                ) {
                   items(categoryMap.entries.size) { categoryIndex ->
                       val id = categoryMap.keys.toList()[categoryIndex]
                       viewModel.getProductByCategory(categoryId = id, differenceMinutes = 1)
                       val findProduct = allProductsList.find { it.id == id }
                       Text(
                           categoryMap[id] ?: "",
                           modifier = Modifier
                               .padding(start = 10.dp, top = 25.dp),
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
                               contentPadding = PaddingValues(start = 10.dp)
                           ) {
                               items(findProduct?.results!!.size) { productIndex ->
                                   RowCategoryMap(
                                       item = findProduct.results!![productIndex]
                                   )
                               }
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