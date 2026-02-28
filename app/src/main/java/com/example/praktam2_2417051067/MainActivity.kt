package com.example.praktam2_2417051067

import Model.Butawarna
import Model.ButawarnaSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerId
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.praktam2_2417051067.ui.theme.PrakTAM2_2417051067Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM2_2417051067Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
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
fun Greeting(name: String, modifier: Modifier = Modifier, npm: String) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier. fillMaxSize(). padding(24.dp).verticalScroll(scrollState)) {
        val daftarButawarna = ButawarnaSource . dummyButawarna

                daftarButawarna.forEach { butawarna ->
                    Text(text = "Soal ${butawarna.name}.")

                    Image(
                        painter = painterResource(id = butawarna.ImageRes),
                        contentDescription = butawarna.name,
                        modifier = Modifier.size(200.dp)
                    )


                    Text(text = "Pertanyaan: ${butawarna.pertanyaan}?")
                    Text(text = "Jawaban: ${butawarna.jawaban}")
                    Text(text = "--------------------------")
                }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrakTAM2_2417051067Theme {
        Greeting(name = "Android", npm = "2417051067")
    }
}