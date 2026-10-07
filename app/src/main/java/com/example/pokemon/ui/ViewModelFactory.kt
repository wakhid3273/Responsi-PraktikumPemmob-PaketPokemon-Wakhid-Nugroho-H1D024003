package com.example.pokemon.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.ui.detail.DetailViewModel
import com.example.pokemon.ui.home.HomeViewModel

/**
 * ViewModelFactory untuk inject repository ke ViewModel secara manual
 * Tanpa menggunakan dependency injection framework seperti Hilt
 * 
 * Factory pattern ini memungkinkan kita membuat ViewModel dengan custom constructor parameters
 */
class ViewModelFactory(
    private val repository: PokemonRepository
) : ViewModelProvider.Factory {
    
    /**
     * Create ViewModel berdasarkan class type
     * SavedStateHandle otomatis di-provide oleh Android framework
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                // Create HomeViewModel dengan repository
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                // Create DetailViewModel dengan SavedStateHandle dan repository
                // SavedStateHandle digunakan untuk menerima navigation arguments
                DetailViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    repository = repository
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

/**
 * Helper object untuk menyediakan singleton ViewModelFactory
 * Repository dibuat sekali dan di-reuse untuk semua ViewModel
 */
object ViewModelProvider {
    private val repository = PokemonRepository()
    
    /**
     * Factory instance yang bisa digunakan di Composable:
     * viewModel(factory = ViewModelProvider.factory)
     */
    val factory = ViewModelFactory(repository)
}
