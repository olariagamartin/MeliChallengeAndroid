package com.themarto.features.search

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.PagingDataEvent
import androidx.paging.PagingDataPresenter
import androidx.paging.compose.LazyPagingItems
import app.cash.turbine.test
import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
    fun `1-WHEN ViewModel is initialized THEN searchResult is null`() = runTest {
        val viewModel = SearchVM(repository = provideProductsRepository())
        assert(viewModel.uiState.value.productsResult == null)
    }

    @Test
    fun `2-WHEN onSearch is called THEN products loadState refresh is Loading`() = runTest {
        val viewModel = SearchVM(repository = provideProductsRepository(
            searchProductsFlow = flowOf(
                PagingData.from(
                    data = emptyList(),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.Loading,
                        prepend = LoadState.NotLoading(endOfPaginationReached = true),
                        append = LoadState.NotLoading(endOfPaginationReached = true),
                    )
                )
            )
        ))
        viewModel.onSearch()
        advanceUntilIdle()

        val pagingDataPresenter = object : PagingDataPresenter<ProductPreview>() {
            override suspend fun presentPagingDataEvent(event: PagingDataEvent<ProductPreview>) { }
        }

        viewModel.uiState.test {
            awaitItem().productsResult?.test {
                awaitItem().let { pagingDataPresenter.collectFrom(it) }
                cancelAndConsumeRemainingEvents()
            }

            pagingDataPresenter.loadStateFlow.test {
                assert(awaitItem()?.refresh is LoadState.Loading)
                cancelAndConsumeRemainingEvents()
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `3-WHEN repository return success THEN searchResult is not null`() =
        runTest {
            val viewModel = SearchVM(repository = provideProductsRepository())
            viewModel.onSearch()
            advanceUntilIdle()
            assert(viewModel.uiState.value.productsResult != null)
        }

    @Test
    fun `4-WHEN repository return error THEN loadState refresh is Error`() = runTest {
        val viewModel = SearchVM(
            repository = provideProductsRepository(
                searchProductsFlow = flowOf(
                    PagingData.from(
                        data = emptyList(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Error(Throwable("error123")),
                            prepend = LoadState.NotLoading(endOfPaginationReached = true),
                            append = LoadState.NotLoading(endOfPaginationReached = true),
                        )
                    )
                )
            )
        )
        viewModel.onSearch()
        advanceUntilIdle()

        val pagingDataPresenter = object : PagingDataPresenter<ProductPreview>() {
            override suspend fun presentPagingDataEvent(event: PagingDataEvent<ProductPreview>) { }
        }

        viewModel.uiState.test {
            awaitItem().productsResult?.test {
                awaitItem().let { pagingDataPresenter.collectFrom(it) }
                cancelAndConsumeRemainingEvents()
            }

            pagingDataPresenter.loadStateFlow.test {
                assert(awaitItem()?.refresh is LoadState.Error)
                cancelAndConsumeRemainingEvents()
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `5-WHEN onQueryChange is called THEN searchQueryInput is updated`() = runTest {
        val viewModel = SearchVM(repository = provideProductsRepository())
        viewModel.onQueryChange("query123")
        assert(viewModel.uiState.value.searchQueryInput == "query123")
    }

    @Test
    fun `6-WHEN onDismiss error is called THEN error is null`() = runTest {
        val viewModel = SearchVM(
            repository = provideProductsRepository(
                searchProductsFlow = flowOf(
                    PagingData.from(
                        data = emptyList(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Error(Throwable("error123")),
                            prepend = LoadState.NotLoading(endOfPaginationReached = true),
                            append = LoadState.NotLoading(endOfPaginationReached = true),
                        )
                    )
                )
            )
        )
        viewModel.onSearch()
        advanceUntilIdle()
        viewModel.onDismissError()
        assert(viewModel.uiState.value.error == null)
    }

    @Test
    fun `7-WHEN onConfirm error is called THEN error is null `() = runTest {
        val viewModel = SearchVM(
            repository = provideProductsRepository(
                searchProductsFlow = flowOf(
                    PagingData.from(
                        data = emptyList(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Error(Throwable("error123")),
                            prepend = LoadState.NotLoading(endOfPaginationReached = true),
                            append = LoadState.NotLoading(endOfPaginationReached = true),
                        )
                    )
                )
            )
        )
        viewModel.onSearch()
        advanceUntilIdle()
        viewModel.onConfirmError()
        assert(viewModel.uiState.value.error == null)
    }

    @Test
    fun `8-WHEN onSearch is called THEN repository search is called with the correct query`() =
        runTest {
            val repository = spy(provideProductsRepository())
            val viewModel = SearchVM(repository = repository)
            viewModel.onQueryChange("query123")
            viewModel.onSearch()
            advanceUntilIdle()
            verify(repository).searchProducts("query123")
        }

}