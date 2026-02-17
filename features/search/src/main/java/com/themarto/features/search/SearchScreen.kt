package com.themarto.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.themarto.core.data.model.ProductPreview
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchVM = koinViewModel(),
    navigateToDetails: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreenContent(
        searchResult = uiState.searchResult,
        query = uiState.searchQueryInput,
        onQueryChange = viewModel::onQueryChange,
        onSearch = viewModel::onSearch,
        navigateToDetails = navigateToDetails,
        error = uiState.error,
        onDismissError = viewModel::onDismissError,
        onConfirmError = viewModel::onConfirmError
    )
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier, searchResult: List<ProductPreview>,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    navigateToDetails: (String) -> Unit,
    error: String? = null,
    onDismissError: () -> Unit = {},
    onConfirmError: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
        )
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = modifier.fillMaxSize(),
        ) {
            items(items = searchResult) {
                SearchResultItemView(
                    item = it,
                    onClick = { navigateToDetails(it.id) }
                )
            }
        }
    }

    error?.let { errorMessage ->
        AlertDialog(
            onDismissRequest = onDismissError,
            title = { Text("Ocurrió un error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = onConfirmError) {
                    Text("Entendido")
                }
            }
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFeed130))
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            modifier = modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(color = Color.White),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text("Buscar productos") },
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch()
                focusManager.clearFocus()
            }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            )
        )
    }
}

@Composable
fun SearchResultItemView(
    modifier: Modifier = Modifier,
    item: ProductPreview,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .size(width = 200.dp, height = 300.dp)
            .padding(vertical = 6.dp, horizontal = 2.dp)
            .clickable(onClick = onClick, indication = null, interactionSource = null)
    ) {
        AsyncImage(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(6.dp)),
            model = item.imageUrl,
            contentDescription = item.title,
            placeholder = ColorPainter(Color.Gray),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = item.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultItemPrev() {
    SearchResultItemView(
        item = ProductPreview(
            id = "1",
            title = "Celular Samsung Samsung Zflip",
            imageUrl = "https://http2.mlstatic.com/D_NQ_NP_631627-MLU77166846506_072024-F.jpg"
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenContentPrev() {
    SearchScreenContent(
        query = "",
        onQueryChange = {},
        navigateToDetails = {},
        onSearch = {},
        searchResult = listOf(
            ProductPreview(
                id = "1",
                title = "Celular Samsung Samsung Zflip",
                imageUrl = "https://http2.mlstatic.com/D_NQ_NP_631627-MLU77166846506_072024-F.jpg"
            ),
            ProductPreview(
                id = "2",
                title = "Macbook Pro M3",
                imageUrl = "https://http2.mlstatic.com/D_NQ_NP_900000-MLU77166846506_072024-F.jpg"
            )
        )
    )
}