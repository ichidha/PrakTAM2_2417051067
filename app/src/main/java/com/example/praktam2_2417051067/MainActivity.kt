package com.example.praktam2_2417051067

import com.example.praktam2_2417051067.data.Model.Butawarna
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.praktam2_2417051067.data.repository.ButawarnaRepository
import com.example.praktam2_2417051067.ui.theme.PrakTAM2_2417051067Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM2_2417051067Theme {
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    TesButaWarna(
                        modifier = Modifier
                            .padding(innerPadding)
                            .imePadding(),
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}

@Composable
fun TesButaWarna(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    val repository = remember { ButawarnaRepository() }
    var daftarSoal by remember { mutableStateOf<List<Butawarna>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    var namaPeserta by remember { mutableStateOf("") }
    var totalBenar by remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            daftarSoal = repository.getSoal()
            isLoading = false
            isError = daftarSoal.isEmpty()
        } catch (e: Exception) {
            isLoading = false
            isError = true
        }
    }

    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Memuat soal...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        return
    }

    if (isError || daftarSoal.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Gagal Memuat Soal",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pastikan koneksi internet Anda menyala",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
        return
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                HeaderSection(namaPeserta, totalBenar, daftarSoal.size)
            }

            item {
                PaddingWrapper {
                    OutlinedTextField(
                        value = namaPeserta,
                        onValueChange = { namaPeserta = it },
                        placeholder = { Text("Masukkan Nama Anda") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

            item {
                Column {
                    PaddingWrapper {
                        Text(
                            text = "Daftar Soal (Preview)",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(daftarSoal) { index, soal ->
                            SmallGalleryItem(
                                butawarna = soal,
                                onClick = {
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(index + 4)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            item {
                PaddingWrapper {
                    Text(
                        text = "Lembar Soal Ishihara",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            items(daftarSoal) { soal ->
                PaddingWrapper {
                    EnhancedQuizCard(
                        butawarna = soal,
                        onCorrect = { totalBenar++ },
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderSection(nama: String, skor: Int, total: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Tes Buta Warna",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = if (nama.isEmpty()) "Halo, Selamat Datang!" else "Halo, $nama!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Surface(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Skor Anda saat ini",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        )
                        Text(
                            text = "$skor dari $total benar",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SmallGalleryItem(butawarna: Butawarna, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                model = butawarna.imageUrl,
                contentDescription = "Soal ${butawarna.name}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.tes1),
                error = painterResource(id = R.drawable.tes1)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "No. ${butawarna.name}",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 11.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Composable
fun EnhancedQuizCard(
    butawarna: Butawarna,
    onCorrect: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    var inputUser by remember { mutableStateOf("") }
    var sudahCek by remember { mutableStateOf(false) }
    var isBenar by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = butawarna.name,
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Angka Berapa Yang Kamu Lihat?",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = butawarna.imageUrl,
                    contentDescription = "Gambar soal ${butawarna.name}",
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = R.drawable.tes1),
                    error = painterResource(id = R.drawable.tes1)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (!sudahCek) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputUser,
                        onValueChange = { inputUser = it },
                        placeholder = { Text("Masukan Angka!") },
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            if (inputUser.isNotEmpty()) {
                                scope.launch {
                                    isLoading = true
                                    delay(1500)
                                    isBenar = inputUser.trim() == butawarna.jawaban.toString()
                                    sudahCek = true
                                    if (isBenar) {
                                        onCorrect()
                                        snackbarHostState.showSnackbar("Hore! Jawaban soal ${butawarna.name} Benar.")
                                    } else {
                                        snackbarHostState.showSnackbar("Yah... Jawaban soal ${butawarna.name} Kurang Tepat.")
                                    }
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.height(56.dp),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            } else {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = if (isBenar) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isBenar) Icons.Default.CheckCircle else Icons.Default.Warning,
                            contentDescription = null,
                            tint = if (isBenar) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isBenar) "Benar!" else "Kurang Tepat",
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                                color = if (isBenar) Color(0xFF2E7D32) else Color(0xFFC62828)
                            )
                            if (!isBenar) {
                                Text(
                                    text = "Jawaban: ${butawarna.jawaban}",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 12.sp),
                                    color = Color(0xFFC62828)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaddingWrapper(content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun ColorBlindPreview() {
    PrakTAM2_2417051067Theme {
        TesButaWarna(snackbarHostState = remember { SnackbarHostState() })
    }
}
