package com.example.pokemon.data.remote

import com.example.pokemon.data.model.PokemonDetailResponse
import com.example.pokemon.data.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface untuk PokéAPI
 * Semua fungsi adalah suspend function untuk digunakan dengan Coroutines
 */
interface PokemonApiService {
    
    /**
     * Mendapatkan list pokemon dengan pagination
     * @param limit jumlah pokemon yang diminta
     * @param offset offset untuk pagination
     */
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 151,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse
    
    /**
     * Mendapatkan detail pokemon berdasarkan nama atau ID
     * @param name nama pokemon (lowercase) atau ID pokemon
     */
    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDetailResponse
}
