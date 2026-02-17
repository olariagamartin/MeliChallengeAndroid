package com.themarto.core.data.network.model

import com.squareup.moshi.Json

data class ProductDTO(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "pictures")
    val pictures: List<PictureDTO>,
    @Json(name = "attributes")
    val attributes: List<AttributeDTO>,
    @Json(name = "short_description")
    val description: DescriptionDTO
)

data class DescriptionDTO(
    @Json(name = "content")
    val content: String
)
