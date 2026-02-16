package com.themarto.features.search

import androidx.lifecycle.ViewModel
import com.themarto.core.data.model.ProductPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class UiState {
    data class SearchResult(
        val searchResult: List<ProductPreview>
    ) : UiState()

    object None : UiState()

    object Loading : UiState()
}

class SearchVM : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.None)
    val uiState: StateFlow<UiState> = _uiState


}