package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pokemon.ui.navigation.AppNavigation
import com.example.pokemon.ui.theme.PokemonTheme

/**
 * MainActivity - Entry point aplikasi Pokemon
 * 
 * Activity ini menggunakan Jetpack Compose untuk UI
 * dan Navigation Compose untuk navigasi antar layar
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge untuk full screen experience
        enableEdgeToEdge()
        
        setContent {
            // Aplikasi theme dengan Material 3
            PokemonTheme {
                // Setup navigation graph
                AppNavigation()
            }
        }
    }
}