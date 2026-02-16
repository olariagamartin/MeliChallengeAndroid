package com.themarto.core.data.repository

import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductPreview

interface ProductsRepository {

    suspend fun searchProducts(query: String): Result<List<ProductPreview>>

    suspend fun getProductDetails(productId: String): Result<Product>

}