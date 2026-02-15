package com.themarto.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun SearchScreenContent(modifier: Modifier = Modifier, searchResult: List<SearchResultItem>) {
    LazyVerticalGrid(
        GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
    ) {
        items(items = searchResult) {
            SearchResultItemView(
                item = it,
            )
        }
    }
}

@Composable
fun SearchResultItemView(
    modifier: Modifier = Modifier,
    item: SearchResultItem,
) {
    Column(
        modifier = modifier
            .size(width = 200.dp, height = 300.dp)
            .padding(vertical = 6.dp, horizontal = 2.dp)
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
        item = SearchResultItem(
            id = "1",
            title = "Celular Samsung Samsung Zflip",
            imageUrl = "https://http2.mlstatic.com/D_NQ_NP_631627-MLU77166846506_072024-F.jpg"
        )
    )
}