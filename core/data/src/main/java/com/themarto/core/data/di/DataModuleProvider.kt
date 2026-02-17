package com.themarto.core.data.di

import com.themarto.core.data.network.ProductsApi
import com.themarto.core.data.network.createRetrofit
import com.themarto.core.data.repository.ProductsRepository
import com.themarto.core.data.repository.ProductsRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

object DataModuleProvider {
    fun getModules() = listOf<Module>(
        networkModule,
        repositoryModule,
    )
}

private val networkModule = module {
    single<Retrofit> {
        createRetrofit()
    }
}

private val repositoryModule = module {
    single<ProductsRepository> {
        ProductsRepositoryImpl(api = get<Retrofit>().create(ProductsApi::class.java))
    }
}