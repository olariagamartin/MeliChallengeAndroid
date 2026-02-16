package com.themarto.features.search

import androidx.lifecycle.ViewModel
import com.themarto.core.data.model.ProductPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val searchResult: List<ProductPreview> = emptyList(),
    val searchQueryInput: String = ""
)

class SearchVM : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        _uiState.value = UiState(searchResult = getFakeData())
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(searchQueryInput = query) }
    }

}