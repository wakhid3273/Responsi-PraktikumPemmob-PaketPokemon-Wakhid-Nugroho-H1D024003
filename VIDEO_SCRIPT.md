# 🎬 Script Video - 3 Menit

**Durasi:** 3 menit  
**Style:** Langsung to the point

---

## 🖼️ SCREEN 1: HOME GRID (0:00 - 1:00) - 60 detik

### LazyVerticalGrid (30 detik)

**File:** `HomeScreen.kt` **Lines:** 171-195

**Script:**
> "Grid 2 kolom ini dibuat pakai LazyVerticalGrid. Line 177 GridCells.Fixed(2) untuk 2 kolom. Line 186 key pokemon.id untuk optimasi recomposition. Line 188-194 items loop render PokemonCard."

---

### PokemonCard (30 detik)

**File:** `PokemonCard.kt` **Lines:** 42-84

**Script:**
> "PokemonCard di line 42 Card Material 3. Line 52 SubcomposeAsyncImage dari Coil untuk load gambar. Line 57-65 loading block dengan CircularProgressIndicator. Line 70-77 nama, line 78-84 ID."

---

## 🖼️ SCREEN 2: DETAIL (1:00 - 2:10) - 70 detik

### Layout & Background (20 detik)

**File:** `DetailScreen.kt` **Lines:** 150-169

**Script:**
> "Line 154 verticalScroll biar bisa di-scroll. Line 156 ambil warna tipe pertama pakai toTypeColor. Line 160-169 Card gambar dengan background warna tipe alpha 0.2."

---

### Type Chips (15 detik)

**File:** `DetailScreen.kt` **Lines:** 198-204  
**File:** `TypeChip.kt` **Line:** 33

**Script:**
> "Line 198-204 Row loop types pakai forEach. TypeChip line 33 ambil warna dari type.toTypeColor extension function."

---

### Stats Animasi (35 detik)

**File:** `StatBar.kt` **Lines:** 28-63

**Script:**
> "Line 28-32 animateFloatAsState tween 1000 milliseconds, progress bar animasi 1 detik. Line 42-70 layout Row, line 47 label, line 56 LinearProgressIndicator pakai animated progress, line 64 value."

---

## 🖼️ SCREEN 3: SEARCH (2:10 - 3:00) - 50 detik

### Search State (20 detik)

**File:** `HomeViewModel.kt` **Lines:** 39-40, 83-85

**Script:**
> "Line 39-40 StateFlow searchQuery. Line 83-85 onSearchQueryChange update query pas user ngetik."

---

### Filter Combine (30 detik)

**File:** `HomeViewModel.kt` **Lines:** 47-65

**Script:**
> "Line 47-65 combine dua StateFlow. Setiap ada yang berubah auto re-calculate. Line 52-56 filter pokemon.name.contains query atau pokemon.id.toString contains query, case insensitive. Bisa search by nama atau ID."

---

## ⏱️ DURASI

| Screen | Durasi |
|--------|--------|
| Grid Home | 60s |
| Detail | 70s |
| Search | 50s |
| **TOTAL** | **180s** |

---

## 📁 FILES

1. `ui/home/HomeScreen.kt`
2. `ui/components/PokemonCard.kt`
3. `ui/detail/DetailScreen.kt`
4. `ui/components/TypeChip.kt`
5. `ui/components/StatBar.kt`
6. `ui/home/HomeViewModel.kt`

**Setup:** Font 16-18, line numbers on, collapse imports.
