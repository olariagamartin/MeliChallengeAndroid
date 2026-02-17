package com.themarto.melichallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.themarto.features.productDetails.ProductDetailsScreen
import com.themarto.features.search.SearchScreen
import com.themarto.melichallenge.ui.theme.MLYellow
import com.themarto.melichallenge.ui.theme.MeliChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(MLYellow.toArgb(), MLYellow.toArgb()),
        )
        setContent {
            MeliChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Destinations.SEARCH_ROUTE
                    ) {
                        searchScreen(navController)
                        productDetailsScreen(navController)
                    }
                }
            }
        }
    }

    private fun NavGraphBuilder.searchScreen(navController: NavHostController) {
        composable(Destinations.SEARCH_ROUTE) {
            SearchScreen(
                navigateToDetails = {
                    navController.navigate(Destinations.productDetails(it))
                }
            )
        }
    }

    private fun NavGraphBuilder.productDetailsScreen(navController: NavHostController) {
        composable(
            route = Destinations.PRODUCT_DETAILS_ROUTE,
            arguments = listOf(
                navArgument(Destinations.Arguments.PRODUCT_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            ProductDetailsScreen(
                productId = backStackEntry.arguments?.getString(Destinations.Arguments.PRODUCT_ID) ?: ""
            )
        }
    }
}

object Destinations {
    const val SEARCH_ROUTE = "search"
    const val PRODUCT_DETAILS = "productDetails"
    const val PRODUCT_DETAILS_ROUTE = "$PRODUCT_DETAILS/{${Arguments.PRODUCT_ID}}"

    object Arguments {
        const val PRODUCT_ID = "productId"
    }

    fun productDetails(productId: String) = "$PRODUCT_DETAILS/$productId"
}