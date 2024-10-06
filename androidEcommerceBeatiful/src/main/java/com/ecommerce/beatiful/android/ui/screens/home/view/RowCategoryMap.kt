package com.ecommerce.beatiful.android.ui.screens.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ecommerce.beatiful.android.ui.theme.fontsOpenSans
import com.ecommerce.beatiful.data.model.AmazonResultSerialization


@Composable
fun RowCategoryMap(item: AmazonResultSerialization) {
    Column(
        modifier = Modifier
            .width(120.dp),
    ) {
        Column(
            modifier = Modifier
                .height(150.dp)
                .width(120.dp)
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.8f)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center

            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(0.9f),
                    model = ImageRequest.Builder(LocalContext.current).data(item.imageUrls?.get(0))
                        .crossfade(true).build(), contentDescription = "Image url",
                    contentScale = ContentScale.FillBounds
                )

                Box(
                    modifier = Modifier
                        .offset(x = 45.dp, y = (-55).dp)
                        .zIndex(2f)
                        .size(25.dp)
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(0.6f),
                            shape = CircleShape
                        )
                        .clickable {

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Filled.FavoriteBorder, contentDescription = "Favorite",
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }

            }

        }
        Text(
            item.title,
            fontFamily = fontsOpenSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.tertiary.copy(0.7f),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            item.price,
            fontFamily = fontsOpenSans, fontWeight = FontWeight.Bold, fontSize = 14.sp,
            color = MaterialTheme.colorScheme.tertiary.copy(0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RowCategoryMapPreview() {
    RowCategoryMap(
        item = AmazonResultSerialization(
            asin = "34343",
            imageUrls = listOf(
                "https://github.com/kenjimaeda54.png"
            ),
            price = "34.0",
            title = "123",
            brand = "123",
            url = "123",
            rating = 4.0,
        )
    )
}