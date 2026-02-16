package com.themarto.core.data.repository

import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.utils.Result

interface ProductsRepository {

    suspend fun searchProducts(query: String): Result<List<ProductPreview>>

    suspend fun getProduct(productId: String): Result<Product>

}