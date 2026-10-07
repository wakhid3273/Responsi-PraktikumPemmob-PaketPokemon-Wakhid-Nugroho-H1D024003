package com.example.pokemon.data.repository

import com.example.pokemon.data.model.Pokemon
import com.example.pokemon.data.model.PokemonDetail
import com.example.pokemon.data.model.Resource
import com.example.pokemon.data.remote.RetrofitInstance
import com.example.pokemon.util.toPokemon
import com.example.pokemon.util.toPokemonDetail
import retrofit2.HttpException
import java.io.IOException

/**
 * Repository untuk menghandle data pokemon
 * Menjadi perantara antara ViewModel dan API Service (MVVM pattern)
 */
class PokemonRepository {
    
    private val api = RetrofitInstance.api
    
    /**
     * Mendapatkan list pokemon (Generation 1: 151 pokemon)
     * Kembalikan dalam bentuk Resource untuk state management
     * 
     * @param limit jumlah pokemon yang diminta (default 151 untuk Gen 1, bisa sampai 1025)
     * @return Resource<List<Pokemon>> - Success dengan data atau Error dengan pesan
     */
    suspend fun getPokemonList(limit: Int = 151): Resource<List<Pokemon>> {
        return try {
            // Panggil API untuk mendapatkan list pokemon
            val response = api.getPokemonList(limit = limit, offset = 0)
            
            // Map setiap PokemonListItem ke Pokemon (UI Model)
            val pokemonList = response.results.map { it.toPokemon() }
            
            // Return success dengan data
            Resource.Success(pokemonList)
            
        } catch (e: IOException) {
            // Error koneksi internet
            Resource.Error("Tidak dapat terhubung ke server. Periksa koneksi internet Anda.")
        } catch (e: HttpException) {
            // Error HTTP (4xx, 5xx)
            Resource.Error("Terjadi kesalahan saat mengambil data: ${e.message()}")
        } catch (e: Exception) {
            // Error lainnya
            Resource.Error("Terjadi kesalahan: ${e.localizedMessage ?: "Unknown error"}")
        }
    }
    
    /**
     * Mendapatkan detail pokemon berdasarkan nama
     * 
     * @param name nama pokemon (lowercase) atau ID
     * @return Resource<PokemonDetail> - Success dengan data atau Error dengan pesan
     */
    suspend fun getPokemonDetail(name: String): Resource<PokemonDetail> {
        return try {
            // Panggil API untuk mendapatkan detail pokemon
            val response = api.getPokemonDetail(name.lowercase())
            
            // Konversi ke UI Model
            val pokemonDetail = response.toPokemonDetail()
            
            // Return success dengan data
            Resource.Success(pokemonDetail)
            
        } catch (e: IOException) {
            // Error koneksi internet
            Resource.Error("Tidak dapat terhubung ke server. Periksa koneksi internet Anda.")
        } catch (e: HttpException) {
            // Error HTTP (4xx, 5xx)
            when (e.code()) {
                404 -> Resource.Error("Pokémon tidak ditemukan.")
                else -> Resource.Error("Terjadi kesalahan saat mengambil data: ${e.message()}")
            }
        } catch (e: Exception) {
            // Error lainnya
            Resource.Error("Terjadi kesalahan: ${e.localizedMessage ?: "Unknown error"}")
        }
    }
}
