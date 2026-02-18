package com.themarto.features.search

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val productsResult: Flow<PagingData<ProductPreview>>? = null,
    val searchQueryInput: String = "",
    val error: String? = null,
)

class SearchVM(
    private val repository: ProductsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(searchQueryInput = query) }
    }

    fun onSearch() {
        _uiState.update {
            it.copy(
                productsResult = repository.searchProducts(_uiState.value.searchQueryInput),
            )
        }
    }

    fun onDismissError() {
        _uiState.update { it.copy(error = null) }
    }

    fun onConfirmError() {
        _uiState.update { it.copy(error = null) }
    }
}