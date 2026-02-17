package com.themarto.features.productDetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductAttribute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetailsScreen(
    productId: String,
    navigateBack: () -> Unit = {},
    viewModel: ProductDetailsVM = koinViewModel { parametersOf(productId) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateBack) {
        if (uiState.navigateBack) navigateBack()
    }

    val product = uiState.product
    val error = uiState.error
    when {
        error != null -> {
            ErrorDialog(
                message = error,
                onDismiss = viewModel::onDismissError,
                onConfirm = viewModel::onConfirmError
            )
        }

        uiState.loading && product == null -> {
            LoadingScreen()
        }

        product != null -> {
            ProductDetailsScreenContent(
                product = product,
                navigateBack = navigateBack
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsScreenContent(
    product: Product,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(pageCount = { product.imageUrls.size })

        ProductDetailsTopBar(
            navigateBack = navigateBack
        )

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
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
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = ColorPainter(Color.Gray),
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(1.dp)
                        .background(Color.LightGray)
                )

                ProductAttributes(
                    attributes = product.attributes,
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(1.dp)
                        .background(Color.LightGray)
                )

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ProductDetailsTopBar(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFeed130))
    ) {
        IconButton(
            onClick = navigateBack,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
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

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ocurrió un error") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Entendido")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailsContentPrev() {
    val dummyAttributes = listOf(
        ProductAttribute("Brand", "Dummy Brand"),
        ProductAttribute("Color", "Black"),
        ProductAttribute("Material", "Plastic")
    )

    val dummyProduct = Product(
        id = "123",
        title = "Dummy Product Title",
        description = "This is a long description for the dummy product, showcasing its features and benefits.",
        imageUrls = listOf(
            "https://http2.mlstatic.com/D_NQ_NP_2X_910385-MLA72793108644_112023-F.webp",
            "https://http2.mlstatic.com/D_NQ_NP_2X_656208-MLU74581454178_022024-F.webp"
        ),
        attributes = dummyAttributes
    )
    ProductDetailsScreenContent(product = dummyProduct)
}