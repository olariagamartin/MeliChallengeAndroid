package com.themarto.features.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SearchVMTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `0-WHEN ViewModel is initialized THEN loading is false`() = runTest {
        val viewModel = SearchVM(repository = provideProductsRepository())
        assert(!viewModel.uiState.value.loading)
    }

    @Test
    fun `1-WHEN ViewModel is initialized THEN searchResult is empty`() = runTest {
        val viewModel = SearchVM(repository = provideProductsRepository())
        assert(viewModel.uiState.value.searchResult.isEmpty())
    }

    @Test
    fun `2-WHEN onSearch is called THEN loading is true`() = runTest {
        val viewModel = SearchVM(repository = provideProductsRepository())
        viewModel.onSearch()
        assert(viewModel.uiState.value.loading)
    }

    @Test
    fun `3-WHEN repository return success THEN searchResult is not empty and loading is false`() = runTest {
        val viewModel = SearchVM(repository = provideProductsRepository())
        viewModel.onSearch()
        advanceUntilIdle()
        assert(viewModel.uiState.value.searchResult.isNotEmpty())
        assert(!viewModel.uiState.value.loading)
    }

}