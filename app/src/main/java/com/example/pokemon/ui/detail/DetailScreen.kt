package com.example.pokemon.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.pokemon.data.model.PokemonDetail
import com.example.pokemon.data.model.PokemonStat
import com.example.pokemon.ui.ViewModelProvider
import com.example.pokemon.ui.components.ErrorView
import com.example.pokemon.ui.components.LoadingView
import com.example.pokemon.ui.components.StatBar
import com.example.pokemon.ui.components.TypeChip
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.ui.theme.toTypeColor
import com.example.pokemon.util.toPokemonNumber

/**
 * DetailScreen - Layar detail Pokemon
 * Stateful Composable yang menggunakan ViewModel
 * 
 * @param onBackClick callback ketika tombol back di-klik
 * @param viewModel DetailViewModel untuk state management
 */
@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = ViewModelProvider.factory)
) {
    // Collect StateFlow dari ViewModel dengan lifecycle-aware
    val uiState by viewModel.detailUiState.collectAsStateWithLifecycle()
    
    // Stateless composable dengan state hoisting
    DetailContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetry = viewModel::retry
    )
}

/**
 * DetailContent - Stateless Composable untuk UI Detail
 * Semua state di-hoist ke parent (DetailScreen)
 * 
 * @param uiState state UI (Loading/Success/Error)
 * @param onBackClick callback ketika tombol back di-klik
 * @param onRetry callback ketika tombol retry di-klik
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    uiState: DetailUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    // Judul berubah sesuai state
                    Text(
                        text = when (uiState) {
                            is DetailUiState.Success -> uiState.detail.name
                            else -> "Detail Pokémon"
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    // Tombol back
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        // Content berdasarkan UI State
        when (uiState) {
            is DetailUiState.Loading -> {
                // State Loading: tampilkan loading indicator
                LoadingView(message = "Memuat detail Pokémon...")
            }
            
            is DetailUiState.Success -> {
                // State Success: tampilkan detail pokemon
                PokemonDetailContent(
                    detail = uiState.detail,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            
            is DetailUiState.Error -> {
                // State Error: tampilkan error view dengan retry
                ErrorView(
                    message = uiState.message,
                    onRetry = onRetry
                )
            }
        }
    }
}

/**
 * PokemonDetailContent - Konten detail Pokemon yang scrollable
 * 
 * @param detail data detail pokemon
 */
@Composable
private fun PokemonDetailContent(
    detail: PokemonDetail,
    modifier: Modifier = Modifier
) {
    // Warna background sesuai tipe pertama pokemon
    val primaryTypeColor = detail.types.firstOrNull()?.toTypeColor() ?: Color.Gray
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Scrollable content
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Gambar Pokemon dalam Card dengan background warna tipe
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = primaryTypeColor.copy(alpha = 0.2f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = detail.imageUrl,
                    contentDescription = detail.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentScale = ContentScale.Fit,
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.size(60.dp),
                            color = primaryTypeColor
                        )
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colorScheme.errorContainer,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "?",
                                style = MaterialTheme.typography.displayLarge,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 2. Nama dan ID Pokemon
        Text(
            text = detail.name,
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Text(
            text = detail.id.toPokemonNumber(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 3. Type Chips
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            detail.types.forEach { type ->
                TypeChip(type = type)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 4. Info Tinggi dan Berat dalam dua kartu
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Card Tinggi
            InfoCard(
                label = "Tinggi",
                value = String.format("%.1f m", detail.heightM),
                modifier = Modifier.weight(1f)
            )
            
            // Card Berat
            InfoCard(
                label = "Berat",
                value = String.format("%.1f kg", detail.weightKg),
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 5. Section Base Stats
        Text(
            text = "Base Stats",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )
        
        // StatBar untuk setiap stat
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                detail.stats.forEach { stat ->
                    StatBar(
                        label = stat.name,
                        value = stat.value,
                        maxValue = 255
                    )
                }
            }
        }
        
        // 6. Section Abilities (Bonus)
        if (detail.abilities.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Abilities",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    detail.abilities.forEach { ability ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Bullet point
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                            
                            Text(
                                text = ability,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
                }
            }
        }
        
        // 7. Base Experience (jika ada)
        detail.baseExperience?.let { exp ->
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Base Experience: $exp",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Bottom spacing
        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * InfoCard - Card untuk menampilkan info tinggi/berat
 * 
 * @param label label info (Tinggi/Berat)
 * @param value nilai info
 */
@Composable
private fun InfoCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Medium
            )
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

// ============================================================
// PREVIEW
// ============================================================

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailContentLoadingPreview() {
    PokemonTheme {
        DetailContent(
            uiState = DetailUiState.Loading,
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailContentSuccessPreview() {
    PokemonTheme {
        DetailContent(
            uiState = DetailUiState.Success(
                detail = PokemonDetail(
                    id = 25,
                    name = "Pikachu",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
                    types = listOf("Electric"),
                    heightM = 0.4,
                    weightKg = 6.0,
                    stats = listOf(
                        PokemonStat("HP", 35),
                        PokemonStat("Attack", 55),
                        PokemonStat("Defense", 40),
                        PokemonStat("Sp. Attack", 50),
                        PokemonStat("Sp. Defense", 50),
                        PokemonStat("Speed", 90)
                    ),
                    abilities = listOf("Static", "Lightning Rod"),
                    baseExperience = 112
                )
            ),
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailContentErrorPreview() {
    PokemonTheme {
        DetailContent(
            uiState = DetailUiState.Error("Pokémon tidak ditemukan."),
            onBackClick = {},
            onRetry = {}
        )
    }
}
