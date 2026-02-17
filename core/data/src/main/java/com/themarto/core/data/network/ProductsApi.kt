package com.themarto.core.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {

    private object Products {
        const val PRODUCTS = "products"
        const val SEARCH = "$PRODUCTS/search"
    }

    @GET(Products.SEARCH)
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("status") status: String = "active",
        @Query("site_id") siteId: String = "MLA",
        //@Query("limit") limit: Int = 20,
    ): SearchResponseDTO

}