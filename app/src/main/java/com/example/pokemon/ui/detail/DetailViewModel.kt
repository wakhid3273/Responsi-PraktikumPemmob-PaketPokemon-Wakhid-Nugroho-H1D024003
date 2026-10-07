package com.example.pokemon.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.model.PokemonDetail
import com.example.pokemon.data.model.Resource
import com.example.pokemon.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Sealed interface untuk state UI Detail Screen
 * Memudahkan handling berbagai state (Loading, Success, Error)
 */
sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val detail: PokemonDetail) : DetailUiState
    data class Error(val message: String) : DetailUiState
}

/**
 * ViewModel untuk Detail Screen
 * Mengelola state dan business logic untuk detail pokemon
 * 
 * SavedStateHandle digunakan untuk menerima navigation arguments
 */
class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
) : ViewModel() {
    
    // Ambil nama pokemon dari navigation arguments dengan null safety
    // Key "pokemonName" harus sama dengan yang didefinisikan di NavGraph
    // Gunakan ?: untuk fallback jika argument tidak ada (tidak akan crash)
    private val pokemonName: String = savedStateHandle.get<String>("pokemonName") ?: ""
    
    // State untuk detail UI
    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    
    /**
     * StateFlow untuk UI state detail pokemon
     * 
     * PENTING: Composable yang collect StateFlow ini akan:
     * - Di-recompose setiap kali _detailUiState.value berubah
     * - Menampilkan loading indicator saat state = Loading
     * - Menampilkan detail pokemon saat state = Success
     * - Menampilkan error message saat state = Error
     */
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()
    
    init {
        // Load detail pokemon otomatis saat ViewModel dibuat
        // Hanya load jika pokemonName tidak empty
        if (pokemonName.isNotEmpty()) {
            loadDetail()
        } else {
            // Set error state jika pokemonName tidak ada
            _detailUiState.value = DetailUiState.Error("Pokémon tidak ditemukan.")
        }
    }
    
    /**
     * Load detail pokemon dari repository
     * Menggunakan viewModelScope untuk automatic cancellation
     */
    fun loadDetail() {
        viewModelScope.launch {
            // Set state ke Loading
            // Perubahan ini memicu recomposition untuk menampilkan loading indicator
            _detailUiState.value = DetailUiState.Loading
            
            // Fetch data dari repository
            when (val result = repository.getPokemonDetail(pokemonName)) {
                is Resource.Success -> {
                    // Update state ke Success dengan data detail
                    // Perubahan ini memicu recomposition untuk menampilkan detail pokemon
                    _detailUiState.value = DetailUiState.Success(result.data)
                }
                is Resource.Error -> {
                    // Update state ke Error dengan pesan error
                    // Perubahan ini memicu recomposition untuk menampilkan error message
                    _detailUiState.value = DetailUiState.Error(result.message)
                }
                is Resource.Loading -> {
                    _detailUiState.value = DetailUiState.Loading
                }
            }
        }
    }
    
    /**
     * Retry loading detail (misalnya setelah error)
     */
    fun retry() {
        loadDetail()
    }
}
