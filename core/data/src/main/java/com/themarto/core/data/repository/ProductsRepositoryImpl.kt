package com.themarto.core.data.repository

import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.network.ProductsApi
import com.themarto.core.data.utils.Result
import com.themarto.core.data.utils.toDomain

class ProductsRepositoryImpl(
    private val api: ProductsApi
) : ProductsRepository {
    override suspend fun searchProducts(query: String): Result<List<ProductPreview>> {
        return try {
            Result.Success(api.searchProducts(query).toDomain())
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getProduct(productId: String): Result<Product> {
        return try {
            Result.Success(api.getProduct(productId).toDomain())
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")

        }
    }
}