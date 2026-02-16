package com.themarto.core.data.model

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val imageUrls: List<String>,
    val attributes: List<ProductAttribute>
)

data class ProductAttribute(
    val name: String,
    val value: String
)