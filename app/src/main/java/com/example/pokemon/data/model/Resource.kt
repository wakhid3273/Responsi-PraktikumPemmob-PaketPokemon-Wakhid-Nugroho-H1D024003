package com.example.pokemon.data.model

/**
 * Sealed class untuk membungkus hasil operasi async
 * Digunakan untuk state management di ViewModel
 */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
