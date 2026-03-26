package com.themarto.features.search

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import okio.IOException

data class UiState(
    val productsResult: Flow<PagingData<ProductPreview>>? = null,
    val searchQueryInput: String = "",
    val error: SearchError = SearchError.Null,
)

sealed class SearchError {
    object Unknown : SearchError()
    object Network : SearchError()
    object ServerError : SearchError()
    object Authentication : SearchError()
    object Null : SearchError()
}

class SearchVM(
    private val repository: ProductsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var errorHandled = true

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(searchQueryInput = query) }
    }

    fun onSearch() {
        _uiState.update {
            it.copy(
                productsResult = repository.searchProducts(_uiState.value.searchQueryInput).onEach {
                    errorHandled = false
                },
            )
        }
    }

    fun onDismissError() {
        _uiState.update { it.copy(error = SearchError.Null) }
    }

    fun onConfirmError() {
        _uiState.update { it.copy(error = SearchError.Null) }
    }

    fun onPagingDataError(error: Throwable) {
        if (errorHandled) return

        val searchError = when (error) {
            is retrofit2.HttpException -> httpExceptionToSearchError(error)
            is IOException -> SearchError.Network
            else -> SearchError.Unknown
        }
        _uiState.update { it.copy(error = searchError) }

        errorHandled = true
    }

    private fun httpExceptionToSearchError(httpException: retrofit2.HttpException): SearchError {
        return when (httpException.code()) {
            403 -> SearchError.Authentication
            else -> SearchError.ServerError
        }
    }
}