package com.example.pokemon.data.model

import com.google.gson.annotations.SerializedName

/**
 * Response dari API untuk list pokemon
 * Digunakan untuk endpoint: /pokemon?limit=&offset=
 */
data class PokemonListResponse(
    @SerializedName("count")
    val count: Int,
    
    @SerializedName("results")
    val results: List<PokemonListItem>
)

/**
 * Item individual dalam list pokemon
 */
data class PokemonListItem(
    @SerializedName("name")
    val name: String,
    
    @SerializedName("url")
    val url: String
)
