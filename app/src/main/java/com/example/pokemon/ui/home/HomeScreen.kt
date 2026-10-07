package com.example.pokemon.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemon.data.model.Pokemon
import com.example.pokemon.ui.ViewModelProvider
import com.example.pokemon.ui.components.EmptyView
import com.example.pokemon.ui.components.ErrorView
import com.example.pokemon.ui.components.LoadingView
import com.example.pokemon.ui.components.PokemonCard
import com.example.pokemon.ui.components.SearchBar
import com.example.pokemon.ui.theme.PokemonTheme

/**
 * HomeScreen - Layar utama yang menampilkan daftar Pokemon
 * Stateful Composable yang menggunakan ViewModel
 * 
 * @param onPokemonClick callback ketika pokemon di-klik, menerima nama pokemon
 * @param viewModel HomeViewModel untuk state management
 */
@Composable
fun HomeScreen(
    onPokemonClick: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = ViewModelProvider.factory)
) {
    // Collect StateFlow dari ViewModel
    // collectAsStateWithLifecycle: lifecycle-aware collect yang otomatis
    // subscribe/unsubscribe sesuai lifecycle (efisien untuk memory)
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    
    // Stateless composable dengan state hoisting
    HomeContent(
        uiState = uiState,
        searchQuery = searchQuery,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onPokemonClick = onPokemonClick,
        onRetry = viewModel::retry
    )
}

/**
 * HomeContent - Stateless Composable untuk UI Home
 * Semua state di-hoist ke parent (HomeScreen)
 * 
 * State Hoisting Benefits:
 * - Reusability: bisa dipakai dengan state source lain
 * - Testability: mudah di-test tanpa ViewModel
 * - Single source of truth: state dikelola di satu tempat
 * 
 * @param uiState state UI (Loading/Success/Error)
 * @param searchQuery query pencarian
 * @param onSearchQueryChange callback ketika search query berubah
 * @param onPokemonClick callback ketika pokemon di-klik
 * @param onRetry callback ketika tombol retry di-klik
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: HomeUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onPokemonClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            // Top App Bar dengan judul aplikasi
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Katalog Pokémon",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                modifier = Modifier.padding(16.dp),
                placeholder = "Cari Pokémon berdasarkan nama atau ID..."
            )
            
            // Content berdasarkan UI State
            // when expression untuk handle berbagai state
            when (uiState) {
                is HomeUiState.Loading -> {
                    // State Loading: tampilkan loading indicator
                    LoadingView(message = "Memuat Pokémon...")
                }
                
                is HomeUiState.Success -> {
                    // State Success: tampilkan grid pokemon
                    val pokemons = uiState.pokemons
                    
                    if (pokemons.isEmpty()) {
                        // Jika hasil filter kosong, tampilkan empty view
                        EmptyView(
                            message = if (searchQuery.isNotEmpty()) {
                                "Tidak ada Pokémon yang cocok dengan \"$searchQuery\""
                            } else {
                                "Tidak ada Pokémon yang ditemukan."
                            }
                        )
                    } else {
                        // Tampilkan grid pokemon
                        PokemonGrid(
                            pokemons = pokemons,
                            onPokemonClick = onPokemonClick
                        )
                    }
                }
                
                is HomeUiState.Error -> {
                    // State Error: tampilkan error view dengan retry
                    ErrorView(
                        message = uiState.message,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

/**
 * PokemonGrid - LazyVerticalGrid untuk menampilkan daftar Pokemon
 * 
 * LazyVerticalGrid: composable untuk grid yang lazy-loaded (efisien)
 * Hanya render item yang visible di layar
 * 
 * @param pokemons list pokemon yang akan ditampilkan
 * @param onPokemonClick callback ketika pokemon di-klik
 */
@Composable
private fun PokemonGrid(
    pokemons: List<Pokemon>,
    onPokemonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 kolom fixed
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = pokemons,
            key = { pokemon -> pokemon.id } // Key untuk recomposition optimization
        ) { pokemon ->
            // Pokemon Card untuk setiap item
            PokemonCard(
                pokemon = pokemon,
                onClick = {
                    // Kirim nama pokemon (lowercase) untuk navigation
                    onPokemonClick(pokemon.name.lowercase())
                }
            )
        }
    }
}

// ============================================================
// PREVIEW
// ============================================================

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeContentLoadingPreview() {
    PokemonTheme {
        HomeContent(
            uiState = HomeUiState.Loading,
            searchQuery = "",
            onSearchQueryChange = {},
            onPokemonClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeContentSuccessPreview() {
    PokemonTheme {
        HomeContent(
            uiState = HomeUiState.Success(
                pokemons = listOf(
                    Pokemon(1, "Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"),
                    Pokemon(4, "Charmander", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png"),
                    Pokemon(7, "Squirtle", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png"),
                    Pokemon(25, "Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png")
                )
            ),
            searchQuery = "",
            onSearchQueryChange = {},
            onPokemonClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeContentEmptyPreview() {
    PokemonTheme {
        HomeContent(
            uiState = HomeUiState.Success(pokemons = emptyList()),
            searchQuery = "Digimon",
            onSearchQueryChange = {},
            onPokemonClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeContentErrorPreview() {
    PokemonTheme {
        HomeContent(
            uiState = HomeUiState.Error("Tidak dapat terhubung ke server. Periksa koneksi internet Anda."),
            searchQuery = "",
            onSearchQueryChange = {},
            onPokemonClick = {},
            onRetry = {}
        )
    }
}
