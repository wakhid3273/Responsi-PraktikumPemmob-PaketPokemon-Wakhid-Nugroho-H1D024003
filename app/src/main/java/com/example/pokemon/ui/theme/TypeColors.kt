package com.example.pokemon.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Warna untuk setiap tipe Pokémon
 * Berdasarkan color scheme official Pokémon
 */
object TypeColors {
    val Normal = Color(0xFFA8A878)
    val Fire = Color(0xFFF08030)
    val Water = Color(0xFF6890F0)
    val Electric = Color(0xFFF8D030)
    val Grass = Color(0xFF78C850)
    val Ice = Color(0xFF98D8D8)
    val Fighting = Color(0xFFC03028)
    val Poison = Color(0xFFA040A0)
    val Ground = Color(0xFFE0C068)
    val Flying = Color(0xFFA890F0)
    val Psychic = Color(0xFFF85888)
    val Bug = Color(0xFFA8B820)
    val Rock = Color(0xFFB8A038)
    val Ghost = Color(0xFF705898)
    val Dragon = Color(0xFF7038F8)
    val Dark = Color(0xFF705848)
    val Steel = Color(0xFFB8B8D0)
    val Fairy = Color(0xFFEE99AC)
    
    // Default color untuk tipe yang tidak diketahui
    val Unknown = Color(0xFF68A090)
}

/**
 * Extension function untuk mengkonversi nama tipe (String) ke Color
 * Contoh: "fire".toTypeColor() -> Color(0xFFF08030)
 */
fun String.toTypeColor(): Color {
    return when (this.lowercase()) {
        "normal" -> TypeColors.Normal
        "fire" -> TypeColors.Fire
        "water" -> TypeColors.Water
        "electric" -> TypeColors.Electric
        "grass" -> TypeColors.Grass
        "ice" -> TypeColors.Ice
        "fighting" -> TypeColors.Fighting
        "poison" -> TypeColors.Poison
        "ground" -> TypeColors.Ground
        "flying" -> TypeColors.Flying
        "psychic" -> TypeColors.Psychic
        "bug" -> TypeColors.Bug
        "rock" -> TypeColors.Rock
        "ghost" -> TypeColors.Ghost
        "dragon" -> TypeColors.Dragon
        "dark" -> TypeColors.Dark
        "steel" -> TypeColors.Steel
        "fairy" -> TypeColors.Fairy
        else -> TypeColors.Unknown
    }
}
