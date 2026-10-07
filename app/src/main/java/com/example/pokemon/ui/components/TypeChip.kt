package com.example.pokemon.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.ui.theme.toTypeColor
import java.util.Locale

/**
 * Chip untuk menampilkan tipe Pokemon dengan warna sesuai tipe
 * 
 * @param type nama tipe pokemon (misalnya: fire, water, grass)
 * @param modifier modifier untuk customisasi layout
 */
@Composable
fun TypeChip(
    type: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = type.toTypeColor()
    
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = type.uppercase(Locale.ROOT),
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TypeChipPreview() {
    PokemonTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            TypeChip(type = "Fire")
            TypeChip(type = "Water")
            TypeChip(type = "Grass")
            TypeChip(type = "Electric")
        }
    }
}
