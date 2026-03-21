package com.themarto.features.search

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems

interface PagingItems<T : Any> {
    val itemCount: Int
    operator fun get(index: Int): T?
    val loadState: CombinedLoadStates
}

class RealPagingItems<T : Any>(
    private val lazyPagingItems: LazyPagingItems<T>
) : PagingItems<T> {
    override val itemCount: Int get() = lazyPagingItems.itemCount
    override fun get(index: Int): T? = lazyPagingItems[index]
    override val loadState: CombinedLoadStates get() = lazyPagingItems.loadState
}

class FakePagingItems<T : Any>(
    private val items: List<T>,
    override val loadState: CombinedLoadStates = createCombinedLoadStates()
) : PagingItems<T> {
    override val itemCount: Int get() = items.size
    override fun get(index: Int): T? = items.getOrNull(index)
}

fun <T : Any> LazyPagingItems<T>.asPagingItems(): PagingItems<T> = RealPagingItems(this)

fun createCombinedLoadStates(
    refresh: LoadState = LoadState.NotLoading(endOfPaginationReached = false),
    prepend: LoadState = LoadState.NotLoading(endOfPaginationReached = false),
    append: LoadState = LoadState.NotLoading(endOfPaginationReached = false),
    source: LoadStates = LoadStates(refresh, prepend, append),
    mediator: LoadStates? = null
): CombinedLoadStates = CombinedLoadStates(refresh, prepend, append, source, mediator)
