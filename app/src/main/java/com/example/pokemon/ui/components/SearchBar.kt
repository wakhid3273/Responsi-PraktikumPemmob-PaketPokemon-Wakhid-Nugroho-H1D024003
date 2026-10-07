package com.example.pokemon.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.ui.theme.PokemonTheme

/**
 * Search bar untuk mencari Pokemon berdasarkan nama atau ID
 * 
 * @param query text yang di-input user
 * @param onQueryChange callback ketika query berubah
 * @param modifier modifier untuk customisasi layout
 * @param placeholder placeholder text
 */
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Cari Pokémon..."
) {
    val focusManager = LocalFocusManager.current
    
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(text = placeholder)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            // Tombol clear hanya muncul jika ada text
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // Dismiss keyboard saat search
                focusManager.clearFocus()
            }
        ),
        shape = MaterialTheme.shapes.large
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    PokemonTheme {
        SearchBar(
            query = "Pikachu",
            onQueryChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarEmptyPreview() {
    PokemonTheme {
        SearchBar(
            query = "",
            onQueryChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
