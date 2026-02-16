package com.themarto.features.search

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SearchModuleProvider {
    fun getModules() = listOf(
        module {
            viewModel { SearchVM(repository = get()) }
        }
    )
}