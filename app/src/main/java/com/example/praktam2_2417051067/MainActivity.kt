package com.example.praktam2_2417051067

import Model.Butawarna
import Model.ButawarnaSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktam2_2417051067.ui.theme.PrakTAM2_2417051067Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM2_2417051067Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TesButaWarna(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TesButaWarna(modifier: Modifier = Modifier) {
    val daftarSoal = ButawarnaSource.dummyButawarna
    var namaPeserta by remember { mutableStateOf("") }
    var totalBenar by remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier
        .fillMaxSize()
        .background(Color(0xFFE2DAD1))) {

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
                        leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = null, tint = Color(0xFF2E2B21)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2E2B21),
                            unfocusedBorderColor = Color(0xFF2E2B21).copy(alpha = 0.5f),
                            focusedLabelColor = Color(0xFF2E2B21),
                            cursorColor = Color(0xFF2E2B21)
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
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E2B21)
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
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E2B21)
                    )
                }
            }

            items(daftarSoal) { soal ->
                PaddingWrapper {
                    EnhancedQuizCard(
                        butawarna = soal,
                        onCorrect = { totalBenar++ }
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E2B21)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Tes Buta Warna",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFE2DAD1),
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = if (nama.isEmpty()) "Halo, Selamat Datang!" else "Halo, $nama!",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFE2DAD1).copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Surface(
                color = Color(0xFFC9BD9E),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Skor Anda saat ini", color = Color(0xFF2E2B21), fontSize = 12.sp)
                        Text("$skor dari $total benar", color = Color(0xFF2E2B21), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF2E2B21),
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBEBCC5))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = butawarna.ImageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Composable
fun EnhancedQuizCard(butawarna: Butawarna, onCorrect: () -> Unit) {
    var inputUser by remember { mutableStateOf("") }
    var sudahCek by remember { mutableStateOf(false) }
    var isBenar by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBEBCC5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color(0xFF2E2B21),
                    shape = CircleShape,
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(butawarna.name, fontWeight = FontWeight.Bold, color = Color(0xFFE2DAD1), fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Angka Berapa Yang Kamu Lihat Pada Gambar Di Bawah?",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF2E2B21),
                    fontWeight = FontWeight.Bold
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
                Image(
                    painter = painterResource(id = butawarna.ImageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
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
                        label = { Text("Angka?") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2E2B21),
                            focusedLabelColor = Color(0xFF2E2B21)
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            if (inputUser.isNotEmpty()) {
                                isBenar = inputUser.trim() == butawarna.jawaban.toString()
                                sudahCek = true
                                if (isBenar) onCorrect()
                            }
                        },
                        modifier = Modifier.height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E2B21))
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFFE2DAD1))
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
                                text = if (isBenar) "Hore... Jawaban Kamu Benar!" else "Yah... Jawaban Kamu Kurang Tepat",
                                fontWeight = FontWeight.Bold,
                                color = if (isBenar) Color(0xFF2E7D32) else Color(0xFFC62828)
                            )
                            if (!isBenar) {
                                Text(
                                    text = "Jawaban: ${butawarna.jawaban}",
                                    fontSize = 12.sp,
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
        TesButaWarna()
    }
}
