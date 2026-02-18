package com.themarto.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.network.ProductsApi
import com.themarto.core.data.utils.Result
import com.themarto.core.data.utils.toDomain
import kotlinx.coroutines.flow.Flow

class ProductsRepositoryImpl(
    private val api: ProductsApi
) : ProductsRepository {
    override fun searchProducts(query: String): Flow<PagingData<ProductPreview>> {
        return Pager(
            config = PagingConfig(
                pageSize = ProductPagingSource.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(api, query) }
        ).flow
    }

    override suspend fun getProduct(productId: String): Result<Product> {
        return try {
            Result.Success(api.getProduct(productId).toDomain())
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")

        }
    }
}