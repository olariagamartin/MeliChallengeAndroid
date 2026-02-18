package com.themarto.features.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themarto.core.data.model.Product
import com.themarto.core.data.repository.ProductsRepository
import com.themarto.core.data.utils.onError
import com.themarto.core.data.utils.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UiState (
    val product: Product? = null,
    val loading: Boolean = false,
    val error: String? = null,
    val navigateBack: Boolean = false
)

class ProductDetailsVM(
    private val repository: ProductsRepository,
    private val productId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(loading = true))
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadProduct()
    }

    private fun loadProduct() {
        _uiState.value = UiState(loading = true)
        viewModelScope.launch {
            repository.getProduct(productId)
                .onSuccess {
                    _uiState.value = UiState(product = it, loading = false)
                }.onError {
                    _uiState.value = UiState(error = it, loading = false)
                }
        }
    }

    fun onDismissError() {
        _uiState.value = UiState(error = null, navigateBack = true)
    }

    fun onConfirmError() {
        _uiState.value = UiState(error = null, navigateBack = true)
    }
}