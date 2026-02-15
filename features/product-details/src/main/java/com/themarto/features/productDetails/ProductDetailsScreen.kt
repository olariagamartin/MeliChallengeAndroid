package com.themarto.features.productDetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsScreenContent(
    productDetails: ProductDetails,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val pagerState = rememberPagerState(pageCount = { productDetails.imageUrls.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) { page ->
            AsyncImage(
                model = productDetails.imageUrls[page],
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = productDetails.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            productDetails.attributes.forEach { attribute ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${attribute.name}: ",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(0.3f)
                    )
                    Text(
                        text = attribute.value,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(0.7f)
                    )
                }
            }

            Text(
                text = productDetails.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
