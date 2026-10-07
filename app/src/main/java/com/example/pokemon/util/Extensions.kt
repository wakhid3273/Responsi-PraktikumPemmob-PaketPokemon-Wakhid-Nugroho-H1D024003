package com.example.pokemon.util

import com.example.pokemon.data.model.Pokemon
import com.example.pokemon.data.model.PokemonDetail
import com.example.pokemon.data.model.PokemonDetailResponse
import com.example.pokemon.data.model.PokemonListItem
import com.example.pokemon.data.model.PokemonStat

/**
 * Extension function untuk capitalize huruf pertama
 * Contoh: "pikachu" -> "Pikachu"
 */
fun String.capitalizeFirst(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

/**
 * Extension function untuk mengkonversi PokemonListItem ke Pokemon (UI Model)
 * Mengambil ID dari URL dan membuat imageUrl dari official artwork
 */
fun PokemonListItem.toPokemon(): Pokemon {
    // URL format: https://pokeapi.co/api/v2/pokemon/25/
    // Ambil ID dari segmen terakhir sebelum trailing slash
    val id = url.trimEnd('/').split("/").last().toIntOrNull() ?: 0
    
    // Official artwork URL dari PokeAPI sprites repository
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    
    return Pokemon(
        id = id,
        name = name.capitalizeFirst(),
        imageUrl = imageUrl
    )
}

/**
 * Extension function untuk mengkonversi PokemonDetailResponse ke PokemonDetail (UI Model)
 * Konversi satuan: height (dm -> m), weight (hg -> kg)
 */
fun PokemonDetailResponse.toPokemonDetail(): PokemonDetail {
    // Konversi height dari decimeter ke meter (1m = 10dm)
    val heightInMeters = height / 10.0
    
    // Konversi weight dari hectogram ke kilogram (1kg = 10hg)
    val weightInKg = weight / 10.0
    
    // Extract types
    val typeList = types.map { it.type.name.capitalizeFirst() }
    
    // Extract stats dengan nama yang lebih readable
    val statList = stats.map { statSlot ->
        PokemonStat(
            name = formatStatName(statSlot.stat.name),
            value = statSlot.baseStat
        )
    }
    
    // Extract abilities
    val abilityList = abilities.map { it.ability.name.capitalizeFirst().replace("-", " ") }
    
    // Ambil image URL dari official artwork, atau fallback ke URL berdasarkan ID
    val imageUrl = sprites.other?.officialArtwork?.frontDefault
        ?: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    
    return PokemonDetail(
        id = id,
        name = name.capitalizeFirst(),
        imageUrl = imageUrl,
        types = typeList,
        heightM = heightInMeters,
        weightKg = weightInKg,
        stats = statList,
        abilities = abilityList,
        baseExperience = baseExperience
    )
}

/**
 * Helper function untuk format nama stat agar lebih readable
 */
private fun formatStatName(statName: String): String {
    return when (statName) {
        "hp" -> "HP"
        "attack" -> "Attack"
        "defense" -> "Defense"
        "special-attack" -> "Sp. Attack"
        "special-defense" -> "Sp. Defense"
        "speed" -> "Speed"
        else -> statName.capitalizeFirst().replace("-", " ")
    }
}

/**
 * Extension function untuk format ID menjadi format Pokemon number
 * Contoh: 1 -> "#001", 25 -> "#025", 150 -> "#150"
 */
fun Int.toPokemonNumber(): String {
    return "#${this.toString().padStart(3, '0')}"
}
