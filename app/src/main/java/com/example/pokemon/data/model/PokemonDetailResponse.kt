package com.example.pokemon.data.model

import com.google.gson.annotations.SerializedName

/**
 * Response detail pokemon dari API
 * Digunakan untuk endpoint: /pokemon/{name}
 */
data class PokemonDetailResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("height")
    val height: Int, // dalam decimeter (dm)
    
    @SerializedName("weight")
    val weight: Int, // dalam hectogram (hg)
    
    @SerializedName("base_experience")
    val baseExperience: Int?,
    
    @SerializedName("types")
    val types: List<TypeSlot>,
    
    @SerializedName("stats")
    val stats: List<StatSlot>,
    
    @SerializedName("abilities")
    val abilities: List<AbilitySlot>,
    
    @SerializedName("sprites")
    val sprites: Sprites
)

/**
 * Tipe pokemon (misalnya: fire, water, grass)
 */
data class TypeSlot(
    @SerializedName("slot")
    val slot: Int,
    
    @SerializedName("type")
    val type: Type
)

data class Type(
    @SerializedName("name")
    val name: String
)

/**
 * Stat pokemon (HP, Attack, Defense, dll)
 */
data class StatSlot(
    @SerializedName("base_stat")
    val baseStat: Int,
    
    @SerializedName("stat")
    val stat: Stat
)

data class Stat(
    @SerializedName("name")
    val name: String
)

/**
 * Ability pokemon
 */
data class AbilitySlot(
    @SerializedName("ability")
    val ability: Ability,
    
    @SerializedName("is_hidden")
    val isHidden: Boolean
)

data class Ability(
    @SerializedName("name")
    val name: String
)

/**
 * Sprites (gambar) pokemon
 */
data class Sprites(
    @SerializedName("other")
    val other: Other?
)

data class Other(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String? // nullable karena tidak semua pokemon punya gambar
)
