package com.themarto.features.productDetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.themarto.core.data.model.Product

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsVM = ProductDetailsVM()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val product = uiState.product
    if (uiState.loading || product == null) {
        LoadingScreen()
    } else {
        ProductDetailsScreenContent(
            product = product
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsScreenContent(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val pagerState = rememberPagerState(pageCount = { product.imageUrls.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) { page ->
            AsyncImage(
                model = product.imageUrls[page],
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            product.attributes.forEach { attribute ->
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
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}