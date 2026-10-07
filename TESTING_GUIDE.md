# Panduan Testing Aplikasi Pokémon

## 📍 Lokasi APK

APK telah berhasil di-build dan tersedia di:
```
app/build/outputs/apk/debug/app-debug.apk
```
Ukuran: ~19.5 MB

## 🚀 Cara Install APK

### Opsi 1: Via Android Studio
1. Buka Android Studio
2. Jalankan emulator atau hubungkan device fisik (enable USB debugging)
3. Run project dengan klik tombol **Run** ▶️ atau tekan **Shift+F10**
4. Pilih device target
5. Tunggu hingga aplikasi terinstall dan terbuka otomatis

### Opsi 2: Via Command Line (ADB)
1. Pastikan device terhubung atau emulator running
2. Verifikasi device terdeteksi:
   ```bash
   adb devices
   ```
3. Install APK:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```
4. Atau via Gradle:
   ```bash
   ./gradlew installDebug
   ```

### Opsi 3: Transfer ke Device Fisik
1. Copy file `app-debug.apk` ke device
2. Buka File Manager di device
3. Tap file APK
4. Izinkan "Install from Unknown Sources" jika diminta
5. Tap "Install"

## ✅ Skenario Testing

### 1. Home Screen - List Pokémon

**Test Case 1.1: Load Initial Data**
- [ ] Buka aplikasi
- [ ] Verifikasi loading indicator muncul
- [ ] Verifikasi 151 Pokémon muncul dalam grid 2 kolom
- [ ] Setiap card menampilkan: gambar, nama, dan ID (#001, #002, dst)

**Test Case 1.2: Search by Name**
- [ ] Tap search bar
- [ ] Ketik "pikachu"
- [ ] Verifikasi hanya Pikachu yang muncul
- [ ] Ketik "BULBASAUR" (uppercase)
- [ ] Verifikasi case-insensitive search berfungsi
- [ ] Tap X (clear) untuk reset

**Test Case 1.3: Search by ID**
- [ ] Ketik "25" di search bar
- [ ] Verifikasi Pikachu muncul (ID #025)
- [ ] Ketik "1"
- [ ] Verifikasi beberapa Pokémon dengan ID mengandung "1" muncul

**Test Case 1.4: Empty Search Result**
- [ ] Ketik "digimon" di search bar
- [ ] Verifikasi empty state muncul dengan pesan "Tidak ada Pokemon yang cocok dengan \"digimon\""
- [ ] Verifikasi icon SearchOff tampil

**Test Case 1.5: Error Handling**
- [ ] Matikan WiFi/data
- [ ] Force close aplikasi
- [ ] Buka aplikasi lagi
- [ ] Verifikasi error message muncul: "Tidak dapat terhubung ke server..."
- [ ] Tap tombol "Coba Lagi"
- [ ] Nyalakan WiFi/data
- [ ] Verifikasi data berhasil dimuat

### 2. Navigation - Home to Detail

**Test Case 2.1: Click Pokemon Card**
- [ ] Di home screen, tap card Pikachu
- [ ] Verifikasi navigasi ke detail screen
- [ ] Verifikasi loading indicator muncul sebentar
- [ ] Verifikasi detail Pikachu muncul

**Test Case 2.2: Multiple Navigation**
- [ ] Tap back button (←)
- [ ] Verifikasi kembali ke home screen
- [ ] Scroll dan tap Charizard (#006)
- [ ] Verifikasi detail Charizard muncul
- [ ] Tap back
- [ ] Verifikasi state search tetap tersimpan (jika ada)

### 3. Detail Screen - Pokemon Info

**Test Case 3.1: Basic Info Display**
- [ ] Buka detail Bulbasaur (#001)
- [ ] Verifikasi elemen berikut tampil:
  - [ ] Gambar besar Pokémon (official artwork)
  - [ ] Background card sesuai warna tipe (hijau untuk Grass)
  - [ ] Nama "Bulbasaur"
  - [ ] ID "#001"
  - [ ] Type chips: Grass, Poison (warna berbeda)

**Test Case 3.2: Physical Stats**
- [ ] Verifikasi card "Tinggi" menampilkan nilai dalam meter (mis: 0.7 m)
- [ ] Verifikasi card "Berat" menampilkan nilai dalam kilogram (mis: 6.9 kg)
- [ ] Kedua card berdampingan (row layout)

**Test Case 3.3: Base Stats**
- [ ] Verifikasi section "Base Stats" tampil
- [ ] Verifikasi 6 stat bars:
  - [ ] HP
  - [ ] Attack
  - [ ] Defense
  - [ ] Sp. Attack
  - [ ] Sp. Defense
  - [ ] Speed
- [ ] Verifikasi progress bar ter-animasi saat pertama muncul
- [ ] Verifikasi nilai numerik tampil di kanan
- [ ] Verifikasi warna progress bar sesuai theme (merah Pokédex)

**Test Case 3.4: Abilities**
- [ ] Scroll ke bawah
- [ ] Verifikasi section "Abilities" tampil
- [ ] Verifikasi abilities ditampilkan dengan bullet points
- [ ] Test dengan Pokémon lain yang punya 2+ abilities

**Test Case 3.5: Multiple Types**
- [ ] Buka detail Charizard (#006)
- [ ] Verifikasi 2 type chips: Fire dan Flying
- [ ] Verifikasi warna background sesuai tipe pertama (Fire = orange)

**Test Case 3.6: Scrolling**
- [ ] Di detail screen, scroll dari atas ke bawah
- [ ] Verifikasi semua section dapat di-scroll
- [ ] Verifikasi tidak ada konten terpotong
- [ ] Verifikasi smooth scrolling

### 4. UI/UX Elements

**Test Case 4.1: Top App Bar**
- [ ] Di home screen, verifikasi top bar merah dengan judul "Pokédex"
- [ ] Di detail screen, verifikasi top bar menampilkan nama Pokémon
- [ ] Verifikasi back button (←) berfungsi

**Test Case 4.2: Loading States**
- [ ] Verifikasi loading indicator saat fetch list
- [ ] Verifikasi loading indicator saat fetch detail
- [ ] Verifikasi loading message tampil ("Memuat Pokemon...")

**Test Case 4.3: Image Loading**
- [ ] Verifikasi gambar di card loading dengan smooth transition
- [ ] Verifikasi placeholder saat loading (CircularProgressIndicator)
- [ ] Test dengan koneksi lambat (enable network throttling)

**Test Case 4.4: Dark Mode**
- [ ] Aktifkan dark mode di device settings
- [ ] Buka aplikasi
- [ ] Verifikasi tema gelap diterapkan
- [ ] Verifikasi kontras warna tetap bagus
- [ ] Verifikasi gambar tetap terlihat jelas

### 5. Performance & Stability

**Test Case 5.1: Grid Scrolling Performance**
- [ ] Di home screen, scroll cepat dari atas ke bawah
- [ ] Verifikasi tidak ada lag atau stutter
- [ ] Verifikasi gambar loading secara lazy
- [ ] Scroll kembali ke atas
- [ ] Verifikasi gambar yang sudah dimuat tetap tersimpan (cache)

**Test Case 5.2: Orientation Change**
- [ ] Di home screen, rotate device (portrait ↔ landscape)
- [ ] Verifikasi layout menyesuaikan
- [ ] Verifikasi state search tidak hilang
- [ ] Di detail screen, rotate device
- [ ] Verifikasi scroll position tidak hilang

**Test Case 5.3: App Backgrounding**
- [ ] Buka aplikasi, tunggu data load
- [ ] Tekan Home button (minimize app)
- [ ] Buka aplikasi lain
- [ ] Kembali ke aplikasi Pokémon
- [ ] Verifikasi state tetap tersimpan
- [ ] Verifikasi tidak fetch ulang data

**Test Case 5.4: Memory & Battery**
- [ ] Gunakan aplikasi selama 5-10 menit
- [ ] Navigate bolak-balik antar screens
- [ ] Cek memory usage di Settings → Apps → Pokemon
- [ ] Verifikasi tidak ada memory leak
- [ ] Verifikasi battery usage wajar

### 6. Edge Cases

**Test Case 6.1: Network Loss During Usage**
- [ ] Buka aplikasi dengan WiFi ON
- [ ] Tunggu list Pokemon muncul
- [ ] Matikan WiFi
- [ ] Tap Pokemon card
- [ ] Verifikasi error message muncul
- [ ] Nyalakan WiFi
- [ ] Tap "Coba Lagi"
- [ ] Verifikasi detail berhasil dimuat

**Test Case 6.2: Rapid Navigation**
- [ ] Tap Pokemon card berkali-kali dengan cepat
- [ ] Verifikasi tidak ada crash
- [ ] Tap back button berkali-kali
- [ ] Verifikasi tidak ada navigation bug

**Test Case 6.3: Special Characters in Search**
- [ ] Ketik "mr. mime" di search bar
- [ ] Ketik "farfetch'd"
- [ ] Ketik "nidoran♀" atau "nidoran♂"
- [ ] Verifikasi search tetap berfungsi

**Test Case 6.4: First Launch**
- [ ] Uninstall aplikasi
- [ ] Install ulang
- [ ] Buka aplikasi
- [ ] Verifikasi tidak ada crash
- [ ] Verifikasi permission handling (jika ada)

## 🐛 Bug Reporting

Jika menemukan bug, catat informasi berikut:

### Template Bug Report
```
**Bug Title**: [Judul singkat]

**Steps to Reproduce**:
1. [Step 1]
2. [Step 2]
3. [Step 3]

**Expected Behavior**:
[Apa yang seharusnya terjadi]

**Actual Behavior**:
[Apa yang benar-benar terjadi]

**Screenshots**:
[Attach screenshots jika ada]

**Device Info**:
- Device: [Nama device/emulator]
- Android Version: [API level]
- App Version: 1.0

**Additional Context**:
[Informasi tambahan]
```

## ✨ Success Criteria

Aplikasi dinyatakan **LULUS** testing jika:
- ✅ Semua test case di atas PASS (tidak ada yang FAIL)
- ✅ Tidak ada crash atau force close
- ✅ Loading time wajar (<5 detik untuk list, <3 detik untuk detail)
- ✅ UI responsive dan smooth (60 fps)
- ✅ Network error handling berfungsi dengan baik
- ✅ State management konsisten (tidak ada data loss)

## 📊 Testing Checklist Summary

```
[ ] 1. Home Screen - List Pokémon (5 test cases)
[ ] 2. Navigation - Home to Detail (2 test cases)
[ ] 3. Detail Screen - Pokemon Info (6 test cases)
[ ] 4. UI/UX Elements (4 test cases)
[ ] 5. Performance & Stability (4 test cases)
[ ] 6. Edge Cases (4 test cases)

Total: 25 test cases
```

## 🎯 Priority Testing

Jika waktu terbatas, prioritaskan test ini:

### High Priority (Must Test)
1. ✅ Load initial data (Test 1.1)
2. ✅ Search by name (Test 1.2)
3. ✅ Click Pokemon card (Test 2.1)
4. ✅ Basic info display (Test 3.1)
5. ✅ Base stats (Test 3.3)
6. ✅ Error handling (Test 1.5)

### Medium Priority (Should Test)
7. Search by ID (Test 1.3)
8. Multiple navigation (Test 2.2)
9. Physical stats (Test 3.2)
10. Dark mode (Test 4.4)
11. Grid scrolling performance (Test 5.1)

### Low Priority (Nice to Test)
12. Empty search result (Test 1.4)
13. Abilities (Test 3.4)
14. Orientation change (Test 5.2)
15. Edge cases (Test 6.*)

---

**Happy Testing! 🚀**
