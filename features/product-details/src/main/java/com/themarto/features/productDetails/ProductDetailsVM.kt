package com.themarto.features.productDetails

import androidx.lifecycle.ViewModel
import com.themarto.core.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class UiState {
    data class Ready(
        val product: Product
    ) : UiState()

    object Loading : UiState()
}

class ProductDetailsVM : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState
}