package com.example.praktam2_2417051067

import Model.Butawarna
import Model.ButawarnaSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051067.ui.theme.PrakTAM2_2417051067Theme

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
    val scrollState = rememberScrollState()
    val daftarSoal = ButawarnaSource.dummyButawarna
    var namaPeserta by remember { mutableStateOf("") }
    var totalBenar by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE2DAD1))
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2E2B21)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Aplikasi Tes Buta Warna",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFE2DAD1)
                )

                if (namaPeserta.isNotEmpty()) {
                    Text(
                        text = "Peserta: $namaPeserta",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFE2DAD1).copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = Color(0xFFC9BD9E),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Skor: $totalBenar / ${daftarSoal.size}",
                        color = Color(0xFF2E2B21),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        OutlinedTextField(
            value = namaPeserta,
            onValueChange = { namaPeserta = it },
            label = { Text("Siapa nama Anda?") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2E2B21),
                focusedLabelColor = Color(0xFF2E2B21),
                cursorColor = Color(0xFF2E2B21)
            )
        )

        daftarSoal.forEach { soal ->
            QuizItem(
                butawarna = soal,
                onCorrect = { totalBenar++ }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun QuizItem(butawarna: Butawarna, onCorrect: () -> Unit) {
    var inputUser by remember { mutableStateOf("") }
    var sudahCek by remember { mutableStateOf(false) }
    var isBenar by remember { mutableStateOf(false) }
    var isFlagged by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBEBCC5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color.Black)
            ) {
                Image(
                    painter = painterResource(id = butawarna.ImageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                IconButton(
                    onClick = { isFlagged = !isFlagged },
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).background(Color.Black.copy(0.3f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isFlagged) Icons.Filled.Warning else Icons.Outlined.Info,
                        contentDescription = null,
                        tint = if (isFlagged) Color.Yellow else Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Soal ${butawarna.name}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E2B21)
                )
                Text(
                    text = "Angka berapa yang Anda lihat?", 
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF2E2B21)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = inputUser,
                    onValueChange = { if (!sudahCek) inputUser = it },
                    label = { Text("Jawaban") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !sudahCek,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2E2B21),
                        focusedLabelColor = Color(0xFF2E2B21)
                    )
                )

                if (sudahCek) {
                    Surface(
                        modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                        color = if (isBenar) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isBenar) Icons.Filled.CheckCircle else Icons.Filled.Warning,
                                contentDescription = null,
                                tint = if (isBenar) Color(0xFF2E7D32) else Color(0xFFC62828)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isBenar) "Hore Kamu Benar!" else "Yah... Kamu Salah! Jawabannya ${butawarna.jawaban}",
                                color = if (isBenar) Color(0xFF2E7D32) else Color(0xFFC62828),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    Button(
                        onClick = {
                            if (inputUser.isNotEmpty()) {
                                isBenar = inputUser.trim() == butawarna.jawaban.toString()
                                sudahCek = true
                                if (isBenar) onCorrect()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E2B21))
                    ) {
                        Text("Cek Jawaban", color = Color(0xFFE2DAD1))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorBlindPreview() {
    PrakTAM2_2417051067Theme {
        TesButaWarna()
    }
}
