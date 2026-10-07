package com.example.pokemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon.ui.detail.DetailScreen
import com.example.pokemon.ui.home.HomeScreen

/**
 * Sealed class untuk route navigation
 * Menggunakan sealed class untuk type-safety dan menghindari typo
 */
sealed class Screen(val route: String) {
    /**
     * Route untuk Home Screen
     */
    data object Home : Screen("home")
    
    /**
     * Route untuk Detail Screen dengan parameter nama pokemon
     * Format: "detail/{pokemonName}"
     */
    data object Detail : Screen("detail/{pokemonName}") {
        /**
         * Argument key untuk nama pokemon
         */
        const val ARG_POKEMON_NAME = "pokemonName"
        
        /**
         * Helper function untuk membuat route dengan argument
         * @param pokemonName nama pokemon yang akan ditampilkan
         * @return route string dengan pokemon name
         */
        fun createRoute(pokemonName: String): String {
            return "detail/$pokemonName"
        }
    }
}

/**
 * AppNavigation - Main navigation setup untuk aplikasi
 * Mengelola navigasi antara HomeScreen dan DetailScreen
 * 
 * @param modifier modifier untuk customisasi
 * @param navController NavHostController untuk navigation (default: rememberNavController)
 */
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // Home Screen Route
        composable(route = Screen.Home.route) {
            HomeScreen(
                onPokemonClick = { pokemonName ->
                    // Navigate ke Detail Screen dengan pokemon name sebagai argument
                    navController.navigate(Screen.Detail.createRoute(pokemonName))
                }
            )
        }
        
        // Detail Screen Route dengan argument
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(Screen.Detail.ARG_POKEMON_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            DetailScreen(
                onBackClick = {
                    // Navigate back ke Home Screen
                    navController.popBackStack()
                }
            )
        }
    }
}
