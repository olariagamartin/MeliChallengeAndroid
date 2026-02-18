package com.themarto.core.data.repository

import androidx.paging.PagingData
import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.utils.Result
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun searchProducts(query: String): Flow<PagingData<ProductPreview>>

    suspend fun getProduct(productId: String): Result<Product>

}