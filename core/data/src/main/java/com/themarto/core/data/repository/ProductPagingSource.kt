package com.themarto.core.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.network.ProductsApi
import com.themarto.core.data.utils.toDomain
import java.io.IOException

class ProductPagingSource(
    private val productsApi: ProductsApi,
    private val query: String,
) : PagingSource<Int, ProductPreview>() {

    companion object {
        const val API_STARTING_OFFSET = 0
        const val NETWORK_PAGE_SIZE = 20
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductPreview> {
        val offset = params.key ?: API_STARTING_OFFSET
        return try {
            val response = productsApi.searchProducts(
                query = query,
                limit = params.loadSize,
                offset = offset
            )
            val products = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = products,
                prevKey = if (offset == API_STARTING_OFFSET) null else offset - params.loadSize,
                nextKey = if (products.isEmpty()) null else offset + params.loadSize
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductPreview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(NETWORK_PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(NETWORK_PAGE_SIZE)
        }
    }
}