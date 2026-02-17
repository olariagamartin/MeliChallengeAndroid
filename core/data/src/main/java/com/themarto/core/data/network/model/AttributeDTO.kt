package com.themarto.core.data.network.model

import com.squareup.moshi.Json

data class AttributeDTO(
    @Json(name = "name")
    val name: String,
    @Json(name = "value_name")
    val value: String
)
