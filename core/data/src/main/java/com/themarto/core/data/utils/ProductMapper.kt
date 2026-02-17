package com.themarto.core.data.utils

import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductAttribute
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.network.model.ProductDTO
import com.themarto.core.data.network.model.ProductPreviewDTO
import com.themarto.core.data.network.model.SearchResponseDTO


fun SearchResponseDTO.toDomain(): List<ProductPreview> {
    return this.results.map { it.toDomain() }
}

fun ProductPreviewDTO.toDomain(): ProductPreview {
    return ProductPreview(
        id = this.id,
        title = this.title,
        imageUrl = if (this.images.isEmpty()) null else this.images.first().url
    )
}

fun ProductDTO.toDomain(): Product {
    return Product(
        id = this.id,
        title = this.name,
        imageUrls = this.pictures.map { it.url },
        attributes = this.attributes.map { ProductAttribute(it.name, it.value) },
        description = this.description.content
    )
}