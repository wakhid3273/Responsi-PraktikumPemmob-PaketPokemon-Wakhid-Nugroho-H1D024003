package com.example.pokemon.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.ui.theme.PokemonTheme

/**
 * Progress bar untuk menampilkan stat Pokemon dengan animasi
 * 
 * @param label nama stat (misalnya: HP, Attack, Defense)
 * @param value nilai stat pokemon
 * @param maxValue nilai maksimum untuk kalkulasi progress (default 255)
 * @param modifier modifier untuk customisasi layout
 */
@Composable
fun StatBar(
    label: String,
    value: Int,
    maxValue: Int = 255,
    modifier: Modifier = Modifier
) {
    // Hitung progress (0.0 - 1.0)
    val progress = (value.toFloat() / maxValue).coerceIn(0f, 1f)
    
    // Animasi progress dengan smooth transition
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000),
        label = "stat_progress_animation"
    )
    
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label stat (HP, Attack, dll)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.3f)
        )
        
        // Progress bar
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.weight(0.5f),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        
        // Nilai stat
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StatBarPreview() {
    PokemonTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatBar(label = "HP", value = 45)
            StatBar(label = "Attack", value = 49)
            StatBar(label = "Defense", value = 49)
            StatBar(label = "Sp. Attack", value = 65)
            StatBar(label = "Sp. Defense", value = 65)
            StatBar(label = "Speed", value = 45)
        }
    }
}
