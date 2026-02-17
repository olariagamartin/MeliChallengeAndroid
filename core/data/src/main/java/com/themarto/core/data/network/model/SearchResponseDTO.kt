package com.themarto.core.data.network.model

import com.squareup.moshi.Json

data class SearchResponseDTO(
    @Json(name = "results")
    val results: List<ProductPreviewDTO>
)

data class ProductPreviewDTO(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val title: String,
    @Json(name = "pictures")
    val images: List<PictureDTO>,
)
