package com.example.pokemon.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Dark Color Scheme - untuk mode gelap
 * Menggunakan warna yang lebih terang (80) untuk better contrast di background gelap
 */
private val DarkColorScheme = darkColorScheme(
    primary = PokeRed80,
    onPrimary = PokeRed20,
    primaryContainer = PokeRed20,
    onPrimaryContainer = PokeRed80,
    
    secondary = PokeBlue80,
    onSecondary = PokeBlue20,
    secondaryContainer = PokeBlue20,
    onSecondaryContainer = PokeBlue80,
    
    tertiary = PokeYellow80,
    onTertiary = PokeYellow20,
    tertiaryContainer = PokeYellow20,
    onTertiaryContainer = PokeYellow80,
    
    background = DarkBackground,
    onBackground = Color.White,
    surface = DarkSurface,
    onSurface = Color.White,
)

/**
 * Light Color Scheme - untuk mode terang
 * Menggunakan warna yang lebih gelap (40) untuk better contrast di background terang
 */
private val LightColorScheme = lightColorScheme(
    primary = PokeRed40,
    onPrimary = Color.White,
    primaryContainer = PokeRed80,
    onPrimaryContainer = PokeRed20,
    
    secondary = PokeBlue40,
    onSecondary = Color.White,
    secondaryContainer = PokeBlue80,
    onSecondaryContainer = PokeBlue20,
    
    tertiary = PokeYellow40,
    onTertiary = Color.White,
    tertiaryContainer = PokeYellow80,
    onTertiaryContainer = PokeYellow20,
    
    background = LightBackground,
    onBackground = Color(0xFF1C1B1F),
    surface = LightSurface,
    onSurface = Color(0xFF1C1B1F),
)

/**
 * Theme utama aplikasi Pokémon
 * Support dark mode dan dynamic color (Android 12+)
 * 
 * @param darkTheme apakah menggunakan dark theme (default: mengikuti sistem)
 * @param dynamicColor apakah menggunakan dynamic color Android 12+ (default: false untuk konsistensi tema Pokémon)
 */
@Composable
fun PokemonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color dimatikan secara default untuk mempertahankan tema Pokémon yang konsisten
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    // Update status bar color sesuai theme (Android API 23+)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Gunakan WindowInsetsControllerCompat untuk API yang lebih baru
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}