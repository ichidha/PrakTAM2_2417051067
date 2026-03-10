package com.example.praktam2_2417051067

import Model.Butawarna
import Model.ButawarnaSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktam2_2417051067.ui.theme.PrakTAM2_2417051067Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM2_2417051067Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorBlindApp(
                        name = "Nur Ramadhani",
                        npm = "2417051067",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ColorBlindApp(name: String, npm: String, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val daftarButawarna = ButawarnaSource.dummyButawarna

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Aplikasi Tes Buta Warna",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "Nama: $name", style = MaterialTheme.typography.bodyLarge)
                Text(text = "NPM: $npm", style = MaterialTheme.typography.bodyMedium)
            }
        }

        daftarButawarna.forEach { butawarna ->
            ButawarnaItem(butawarna)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ButawarnaItem(butawarna: Butawarna) {
    var showAnswer by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Soal Ke-${butawarna.name}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = butawarna.ImageRes),
                contentDescription = "Gambar Tes",
                modifier = Modifier
                    .size(240.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${butawarna.pertanyaan}?",
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (showAnswer) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Text(
                        text = "Jawaban: ${butawarna.jawaban}",
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = { showAnswer = !showAnswer },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = if (showAnswer) "Sembunyikan Jawaban" else "Cek Jawaban")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorBlindPreview() {
    PrakTAM2_2417051067Theme {
        ColorBlindApp(name = "Nur Ramadhani", npm = "2417051067")
    }
}
