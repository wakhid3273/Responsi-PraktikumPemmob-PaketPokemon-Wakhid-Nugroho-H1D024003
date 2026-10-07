package com.example.pokemon.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.model.Pokemon
import com.example.pokemon.data.model.Resource
import com.example.pokemon.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Sealed interface untuk state UI Home Screen
 * Memudahkan handling berbagai state (Loading, Success, Error)
 */
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val pokemons: List<Pokemon>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

/**
 * ViewModel untuk Home Screen
 * Mengelola state dan business logic untuk list pokemon
 */
class HomeViewModel(
    private val repository: PokemonRepository
) : ViewModel() {
    
    // State internal untuk menyimpan semua pokemon dari API
    private val _allPokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    
    // State untuk status loading/success/error
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    
    // State untuk search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    /**
     * StateFlow untuk UI state yang sudah difilter berdasarkan search query
     * 
     * PENTING: StateFlow adalah observable state holder yang:
     * - Ketika nilainya berubah, semua Composable yang melakukan collect akan di-recompose
     * - combine() menggabungkan 2 flow (_homeUiState dan _searchQuery)
     * - Setiap kali salah satu berubah, hasil filter dihitung ulang
     * - stateIn() mengkonversi cold flow menjadi hot StateFlow yang bisa di-collect di Composable
     */
    val homeUiState: StateFlow<HomeUiState> = combine(
        _homeUiState,
        _searchQuery
    ) { state, query ->
        // Jika state Success dan ada search query, filter pokemon
        if (state is HomeUiState.Success && query.isNotBlank()) {
            val filtered = state.pokemons.filter { pokemon ->
                // Filter berdasarkan nama (case-insensitive) atau ID
                pokemon.name.contains(query, ignoreCase = true) ||
                pokemon.id.toString().contains(query)
            }
            HomeUiState.Success(filtered)
        } else {
            // Jika tidak ada query atau state bukan Success, return state as is
            state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Keep alive 5s after last subscriber
        initialValue = HomeUiState.Loading
    )
    
    init {
        // Load pokemon otomatis saat ViewModel dibuat
        loadPokemon()
    }
    
    /**
     * Update search query
     * Perubahan ini akan memicu recomposition pada Composable yang collect homeUiState
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    
    /**
     * Load list pokemon dari repository
     * Menggunakan viewModelScope untuk automatic cancellation saat ViewModel di-clear
     */
    fun loadPokemon() {
        viewModelScope.launch {
            // Set state ke Loading
            _homeUiState.value = HomeUiState.Loading
            
            // Fetch data dari repository
            when (val result = repository.getPokemonList(limit = 151)) {
                is Resource.Success -> {
                    // Simpan semua pokemon
                    _allPokemons.value = result.data
                    // Update state ke Success
                    // Perubahan ini memicu recomposition di Composable yang collect homeUiState
                    _homeUiState.value = HomeUiState.Success(result.data)
                }
                is Resource.Error -> {
                    // Update state ke Error
                    // Perubahan ini memicu recomposition untuk menampilkan error message
                    _homeUiState.value = HomeUiState.Error(result.message)
                }
                is Resource.Loading -> {
                    _homeUiState.value = HomeUiState.Loading
                }
            }
        }
    }
    
    /**
     * Retry loading pokemon (misalnya setelah error)
     */
    fun retry() {
        loadPokemon()
    }
}
