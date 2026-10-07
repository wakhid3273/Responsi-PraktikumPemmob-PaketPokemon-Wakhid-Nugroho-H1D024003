# 📝 Changelog - Perapian Penamaan Aplikasi

**Tanggal:** 7 Oktober 2026  
**Tujuan:** Konsistensi penamaan sesuai judul tugas "Katalog dan Eksplorasi Pokémon"

---

## 🔄 PERUBAHAN YANG DILAKUKAN

### 1. ✅ Nama Aplikasi di Launcher

**File:** `app/src/main/res/values/strings.xml`

**Before:**
```xml
<string name="app_name">Pokemon</string>
```

**After:**
```xml
<string name="app_name">Katalog Pokémon</string>
```

**Impact:** Nama aplikasi di launcher sekarang "Katalog Pokémon" ✨

---

### 2. ✅ Judul TopAppBar di HomeScreen

**File:** `ui/home/HomeScreen.kt`

**Before:**
```kotlin
Text(
    text = "Pokédex",
    style = MaterialTheme.typography.titleLarge,
    fontWeight = FontWeight.Bold
)
```

**After:**
```kotlin
Text(
    text = "Katalog Pokémon",
    style = MaterialTheme.typography.titleLarge,
    fontWeight = FontWeight.Bold
)
```

**Impact:** Header home screen sekarang "Katalog Pokémon" 📱

---

### 3. ✅ Placeholder SearchBar

**File:** `ui/home/HomeScreen.kt` & `ui/components/SearchBar.kt`

**Before:**
```kotlin
placeholder = "Cari Pokemon berdasarkan nama atau ID..."
placeholder = "Cari Pokemon..."  // default di SearchBar.kt
```

**After:**
```kotlin
placeholder = "Cari Pokémon berdasarkan nama atau ID..."
placeholder = "Cari Pokémon..."  // default di SearchBar.kt
```

**Impact:** Placeholder search bar menggunakan "Pokémon" dengan aksen é 🔍

---

### 4. ✅ Loading Messages

**Files:** 
- `ui/home/HomeScreen.kt`
- `ui/detail/DetailScreen.kt`

**Before:**
```kotlin
LoadingView(message = "Memuat Pokemon...")
LoadingView(message = "Memuat detail Pokemon...")
```

**After:**
```kotlin
LoadingView(message = "Memuat Pokémon...")
LoadingView(message = "Memuat detail Pokémon...")
```

**Impact:** Loading messages menggunakan "Pokémon" dengan aksen é ⏳

---

### 5. ✅ Empty State Messages

**Files:**
- `ui/home/HomeScreen.kt`
- `ui/components/EmptyView.kt` (preview)

**Before:**
```kotlin
"Tidak ada Pokemon yang cocok dengan \"$searchQuery\""
"Tidak ada Pokemon yang ditemukan."
```

**After:**
```kotlin
"Tidak ada Pokémon yang cocok dengan \"$searchQuery\""
"Tidak ada Pokémon yang ditemukan."
```

**Impact:** Empty state messages menggunakan "Pokémon" dengan aksen é 🔍

---

### 6. ✅ Error Messages

**Files:**
- `data/repository/PokemonRepository.kt`
- `ui/detail/DetailViewModel.kt`
- `ui/detail/DetailScreen.kt` (preview)

**Before:**
```kotlin
Resource.Error("Pokemon tidak ditemukan.")
DetailUiState.Error("Pokemon tidak ditemukan.")
```

**After:**
```kotlin
Resource.Error("Pokémon tidak ditemukan.")
DetailUiState.Error("Pokémon tidak ditemukan.")
```

**Impact:** Error messages menggunakan "Pokémon" dengan aksen é ⚠️

---

### 7. ✅ TopAppBar Title di DetailScreen

**File:** `ui/detail/DetailScreen.kt`

**Before:**
```kotlin
text = when (uiState) {
    is DetailUiState.Success -> uiState.detail.name
    else -> "Detail Pokemon"
}
```

**After:**
```kotlin
text = when (uiState) {
    is DetailUiState.Success -> uiState.detail.name
    else -> "Detail Pokémon"
}
```

**Impact:** Default title detail screen menggunakan "Pokémon" dengan aksen é 📄

---

### 8. ✅ README.md Update

**File:** `README.md`

**Updated:** Judul dan semua referensi "Pokemon" di UI text sudah disesuaikan

**Impact:** Dokumentasi konsisten dengan aplikasi 📚

---

## 🚫 YANG TIDAK DIUBAH (Tetap tanpa aksen é)

### ✅ Nama Class & Interfaces
```kotlin
// TIDAK DIUBAH - ini nama class, bukan UI text
data class Pokemon
sealed interface HomeUiState
class PokemonViewModel
class PokemonRepository
interface PokemonApiService
fun PokemonCard()
fun PokemonTheme()
```

### ✅ Package Names
```kotlin
// TIDAK DIUBAH - ini package name
package com.example.pokemon
```

### ✅ Variable Names
```kotlin
// TIDAK DIUBAH - ini nama variabel
val pokemon: Pokemon
val pokemons: List<Pokemon>
private val pokemonName: String
fun onPokemonClick()
```

### ✅ Function Names
```kotlin
// TIDAK DIUBAH - ini nama fungsi
fun getPokemonList()
fun getPokemonDetail()
fun toPokemon()
```

### ✅ Import Statements
```kotlin
// TIDAK DIUBAH - ini import
import com.example.pokemon.data.model.Pokemon
import com.example.pokemon.ui.components.PokemonCard
```

### ✅ Komentar Kode
```kotlin
// TIDAK DIUBAH - komentar developer tetap "pokemon" tanpa aksen
// Load pokemon otomatis saat ViewModel dibuat
// Simpan semua pokemon
// Fetch data dari repository
```

**Alasan:** Class, package, variable, dan function names tidak boleh menggunakan karakter non-ASCII (é) karena:
- Konvensi Kotlin/Java
- Compatibility issues
- Best practices

---

## 📊 RINGKASAN PERUBAHAN

| Kategori | Before | After | Status |
|----------|--------|-------|--------|
| App Name | Pokemon | Katalog Pokémon | ✅ |
| TopAppBar Home | Pokédex | Katalog Pokémon | ✅ |
| TopAppBar Detail | Detail Pokemon | Detail Pokémon | ✅ |
| Search Placeholder | Cari Pokemon... | Cari Pokémon... | ✅ |
| Loading Messages | Memuat Pokemon... | Memuat Pokémon... | ✅ |
| Empty Messages | ...Pokemon... | ...Pokémon... | ✅ |
| Error Messages | Pokemon tidak... | Pokémon tidak... | ✅ |
| Class Names | Pokemon | Pokemon | ✅ Tidak diubah |
| Package Names | com.example.pokemon | com.example.pokemon | ✅ Tidak diubah |
| Variable Names | pokemon, pokemons | pokemon, pokemons | ✅ Tidak diubah |

---

## ✅ BUILD STATUS

```
✅ BUILD SUCCESSFUL in 4m 4s
✅ 95 actionable tasks executed
✅ 73 executed, 22 up-to-date
✅ 0 errors
✅ 0 warnings
```

---

## 🎯 HASIL AKHIR

### UI Text Consistency
✅ Semua text yang dilihat user menggunakan "Pokémon" (dengan é)  
✅ Nama aplikasi: "Katalog Pokémon"  
✅ Judul screen: "Katalog Pokémon" dan "Detail Pokémon"  
✅ Messages: Loading, error, empty state semua gunakan "Pokémon"  

### Code Consistency
✅ Semua class, package, variable tetap "Pokemon" (tanpa é)  
✅ Mengikuti Kotlin/Java naming conventions  
✅ Tidak ada breaking changes  
✅ Kompatibilitas terjaga  

---

## 📝 CATATAN

1. **Aksen é di UI Text:** Meningkatkan profesionalisme dan akurasi penamaan (Pokémon adalah merek dagang resmi dengan é)

2. **Tanpa Aksen di Code:** Mengikuti best practices programming dan menghindari encoding issues

3. **Konsistensi:** Pemisahan yang jelas antara user-facing text dan code internal

4. **No Breaking Changes:** Tidak ada perubahan logika, fitur, atau arsitektur

---

**Perubahan ini memastikan aplikasi sesuai dengan judul tugas "Katalog dan Eksplorasi Pokémon" sambil tetap mengikuti best practices coding.** ✨
