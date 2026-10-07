package com.example.pokemon.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.ui.theme.PokemonTheme

/**
 * View untuk menampilkan error message dengan tombol retry
 * 
 * @param message pesan error yang ditampilkan
 * @param onRetry callback ketika tombol retry di-klik
 * @param modifier modifier untuk customisasi layout
 */
@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Error icon
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = "Error",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.error
        )
        
        // Error message
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        
        // Retry button
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = "Coba Lagi")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorViewPreview() {
    PokemonTheme {
        ErrorView(
            message = "Tidak dapat terhubung ke server. Periksa koneksi internet Anda.",
            onRetry = {}
        )
    }
}
