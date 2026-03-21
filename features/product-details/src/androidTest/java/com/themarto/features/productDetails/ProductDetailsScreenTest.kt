package com.themarto.features.productDetails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.themarto.core.data.model.Product
import com.themarto.core.data.model.ProductAttribute
import org.junit.Rule
import org.junit.Test

class ProductDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyProduct = Product(
        id = "123",
        title = "Dummy Product Title",
        description = "This is a long description for the dummy product.",
        imageUrls = listOf("https://example.com/image.jpg"),
        attributes = listOf(
            ProductAttribute("Brand", "Dummy Brand"),
            ProductAttribute("Color", "Black")
        )
    )

    @Test
    fun productDetailsContent_isDisplayedCorrectly() {
        composeTestRule.setContent {
            ProductDetailsScreenContent(
                product = dummyProduct,
                navigateBack = {}
            )
        }

        composeTestRule.onNodeWithText("Dummy Product Title").assertIsDisplayed()

        composeTestRule.onNodeWithText("This is a long description for the dummy product.").assertIsDisplayed()

        composeTestRule.onNodeWithText("Características").assertIsDisplayed()
    }

    @Test
    fun clickingAttributes_expandsAndShowsAttributes() {
        composeTestRule.setContent {
            ProductDetailsScreenContent(
                product = dummyProduct,
                navigateBack = {}
            )
        }

        composeTestRule.onNodeWithText("Características").performClick()

        composeTestRule.onNodeWithText("Brand: ").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dummy Brand").assertIsDisplayed()
        composeTestRule.onNodeWithText("Color: ").assertIsDisplayed()
        composeTestRule.onNodeWithText("Black").assertIsDisplayed()
    }

    @Test
    fun clickingBack_triggersNavigateBack() {
        var backClicked = false
        composeTestRule.setContent {
            ProductDetailsScreenContent(
                product = dummyProduct,
                navigateBack = { backClicked = true }
            )
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        assert(backClicked)
    }

    @Test
    fun errorDialog_isDisplayedWithMessage() {
        val errorMessage = "Error loading product"
        var dismissClicked = false
        var confirmClicked = false

        composeTestRule.setContent {
            ErrorDialog(
                message = errorMessage,
                onDismiss = { dismissClicked = true },
                onConfirm = { confirmClicked = true }
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText("Ocurrió un error").assertIsDisplayed()
        
        composeTestRule.onNodeWithText("Entendido").performClick()
        assert(confirmClicked)
    }
}
