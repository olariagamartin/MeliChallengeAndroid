package com.themarto.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themarto.core.data.model.ProductPreview
import com.themarto.core.data.repository.ProductsRepository
import com.themarto.core.data.utils.onError
import com.themarto.core.data.utils.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val searchResult: List<ProductPreview> = emptyList(),
    val searchQueryInput: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

class SearchVM(
    private val repository: ProductsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(searchQueryInput = query) }
    }

    fun onSearch() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            repository.searchProducts(_uiState.value.searchQueryInput)
                .onSuccess { productList ->
                    _uiState.update { it.copy(searchResult = productList, loading = false) }
                }.onError { errorMessage ->
                    _uiState.update { it.copy(error = errorMessage, loading = false) }
                }
        }
    }

    fun onDismissError() {
        _uiState.update { it.copy(error = null) }
    }

    fun onConfirmError() {
        _uiState.update { it.copy(error = null) }
    }
}