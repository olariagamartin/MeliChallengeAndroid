package com.themarto.features.productDetails

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ProductDetailsModuleProvider {
    fun getModules() = listOf(
        module {
            viewModel { (productId: String) ->
                ProductDetailsVM(repository = get(), productId)
            }
        }
    )
}