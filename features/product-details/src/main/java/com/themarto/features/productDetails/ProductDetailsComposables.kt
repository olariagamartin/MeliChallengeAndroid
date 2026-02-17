package com.themarto.features.productDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.themarto.core.data.model.ProductAttribute

@Composable
fun ProductAttributes(
    modifier: Modifier = Modifier,
    attributes: List<ProductAttribute>,
) {
    Column(
        modifier = modifier.padding(2.dp)
    ) {
        Text(
            text = "Características",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Column(
            modifier = Modifier
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp))

        ) {
            attributes.forEachIndexed { index, attribute ->
                val bgColor = if (index % 2 == 0) {
                    Color.White
                } else {
                    Color(0xFFF5F5F5)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(bgColor),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${attribute.name}: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(horizontal = 8.dp)
                    )
                    Text(
                        text = attribute.value,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductAttributesPreview() {
    val sampleAttributes = listOf(
        ProductAttribute(name = "Brand", value = "Apple"),
        ProductAttribute(name = "Model", value = "iPhone 15 Pro Max"),
        ProductAttribute(name = "Color", value = "Natural Titanium"),
        ProductAttribute(name = "Storage", value = "256 GB")
    )
    ProductAttributes(attributes = sampleAttributes)
}