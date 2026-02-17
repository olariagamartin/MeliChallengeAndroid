package com.themarto.features.productDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = LocalIndication.current,
                    interactionSource = remember { MutableInteractionSource() }) {
                    expanded = !expanded
                }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Características",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (expanded) "Collapse" else "Expand"
            )
        }
        AnimatedVisibility(visible = expanded) {
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