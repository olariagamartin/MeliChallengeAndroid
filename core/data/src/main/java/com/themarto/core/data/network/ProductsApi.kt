package com.themarto.core.data.network

import com.themarto.core.data.network.model.ProductDTO
import com.themarto.core.data.network.model.SearchResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApi {

    companion object {
        const val PRODUCTS = "products"

        object Products {
            const val SEARCH = "$PRODUCTS/search"
            const val ID = "$PRODUCTS/{${PathParam.ID}}"
        }

        private object PathParam {
            const val ID = "id"
        }
    }

    @GET(Products.SEARCH)
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("status") status: String = "active",
        @Query("site_id") siteId: String = "MLA",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): SearchResponseDTO

    @GET(Products.ID)
    suspend fun getProduct(
        @Path("id") productId: String,
    ): ProductDTO

}