package com.themarto.features.productDetails

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
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsVMTest {

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
    fun `0-WHEN ViewModel is created THEN loading is true and product is null`() = runTest {
        val viewModel = ProductDetailsVM(
            repository = provideProductsRepository(), productId = "123"
        )
        assert(viewModel.uiState.value.loading)
        assert(viewModel.uiState.value.product == null)
    }

    @Test
    fun `1-WHEN ViewModel is initialized THEN repository getProduct is called`() = runTest {
        val repository = spy(provideProductsRepository())
        val viewModel = ProductDetailsVM(
            repository = repository, productId = "123"
        )
        advanceUntilIdle()
        verify(repository).getProduct("123")
    }

    @Test
    fun `2-WHEN repository return success THEN product is not null`() = runTest {
        val viewModel = ProductDetailsVM(
            repository = provideProductsRepository(), productId = "123"
        )
        advanceUntilIdle()
        assert(viewModel.uiState.value.product != null)
    }
}