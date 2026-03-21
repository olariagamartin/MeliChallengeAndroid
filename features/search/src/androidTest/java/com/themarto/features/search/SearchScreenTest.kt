package com.themarto.features.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.themarto.core.data.model.ProductPreview
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialView_isDisplayed_whenProductsAreNull() {
        composeTestRule.setContent {
            SearchScreenContent(
                products = null,
                query = "",
                onQueryChange = {},
                onSearch = {},
                navigateToDetails = {}
            )
        }

        composeTestRule.onNodeWithText("Busca productos fácilmente").assertIsDisplayed()
    }

    @Test
    fun searchBar_updatesValue_onTextInput() {
        val queryState = mutableStateOf("")

        composeTestRule.setContent {
            SearchScreenContent(
                products = null,
                query = queryState.value,
                onQueryChange = { queryState.value = it },
                onSearch = {},
                navigateToDetails = {}
            )
        }

        val testQuery = "iPhone"
        composeTestRule.onNodeWithText("Buscar productos").performTextInput(testQuery)

        composeTestRule.onNodeWithText(testQuery).assertIsDisplayed()
    }

    @Test
    fun clickingProduct_triggersNavigation() {
        var navigatedProductId: String? = null
        val testProduct = ProductPreview(
            id = "123",
            title = "MacBook Pro",
            imageUrl = ""
        )

        val fakePagingItems = FakePagingItems(listOf(testProduct))

        composeTestRule.setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                SearchScreenContent(
                    products = fakePagingItems,
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    navigateToDetails = { navigatedProductId = it }
                )
            }
        }

        composeTestRule.onNodeWithText("MacBook Pro").performClick()

        assert(navigatedProductId == "123")
    }
}
