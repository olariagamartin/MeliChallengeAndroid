package com.themarto.features.search

import androidx.paging.PagingData
import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductAttribute
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.repository.ProductsRepository
import com.themarto.core.data.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun provideProductsRepository(
    searchProductsFlow: Flow<PagingData<ProductPreview>> = provideSearchProductsFlow(),
    getProduct: suspend (productId: String) -> Result<Product> = provideGetProduct()
): ProductsRepository {
    return object : ProductsRepository {
        override fun searchProducts(query: String): Flow<PagingData<ProductPreview>> {
            return searchProductsFlow
        }

        override suspend fun getProduct(productId: String): Result<Product> {
            return getProduct(productId)
        }

    }
}

fun provideSearchProductsFlow(): Flow<PagingData<ProductPreview>> {
    return flowOf(PagingData.from(
            List(20) {
                ProductPreview(
                    id = it.toString(),
                    title = "Celular Samsung Samsung Zflip",
                    imageUrl = "https://http2.mlstatic.com/D_NQ_NP_631627-MLU77166846506_072024-F.jpg"
                )
            }
        )
    )
}

fun provideGetProduct(): suspend (productId: String) -> Result<Product> {
    return { productId ->
        Result.Success(
            Product(
                id = productId,
                title = "Celular Samsung Samsung Zflip",
                imageUrls = listOf(
                    "https://http2.mlstatic.com/D_NQ_NP_631627-MLU77166846506_072024-F.jpg",
                    "https://http2.mlstatic.com/D_NQ_NP_631627-MLU77166846506_072024-F.jpg",
                    "https://http2.mlstatic.com/D_NQ_NP_631627-MLU77166846506_072024-F.jpg",
                    "https://http2.mlstatic.com/D_NQ_NP_631627-MLU77166846506_072024-F.jpg",
                ),
                attributes = listOf(
                    ProductAttribute(
                        name = "Marca",
                        value = "Samsung"
                    ),
                    ProductAttribute(
                        name = "Modelo",
                        value = "Zflip"
                    ),
                    ProductAttribute(
                        name = "Memoria",
                        value = "128 GB"
                    ),
                    ProductAttribute(
                        name = "Color",
                        value = "Negro"
                    ),
                ),
                description = "Celular Samsung Samsung Zflip"
            )
        )

    }
}