package com.example.pokemon.data.model

/**
 * UI Model untuk menampilkan detail pokemon
 * Model ini digunakan di DetailScreen
 */
data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val heightM: Double, // dalam meter
    val weightKg: Double, // dalam kilogram
    val stats: List<PokemonStat>,
    val abilities: List<String>,
    val baseExperience: Int?
)

/**
 * Stat individual pokemon untuk ditampilkan di UI
 */
data class PokemonStat(
    val name: String,
    val value: Int
)
