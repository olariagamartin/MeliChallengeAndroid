package com.themarto.core.data.utils

import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.network.ProductPreviewDTO
import com.themarto.core.data.network.SearchResponseDTO


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