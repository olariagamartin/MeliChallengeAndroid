package com.themarto.features.productDetails

import androidx.lifecycle.ViewModel
import com.themarto.core.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UiState (
    val product: Product? = null,
    val loading: Boolean = false
)

class ProductDetailsVM : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(loading = true))
    val uiState: StateFlow<UiState> = _uiState
}