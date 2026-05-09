package com.example.praktam2_2417051067.data.api

import com.example.praktam2_2417051067.data.Model.Butawarna
import retrofit2.http.GET

interface ApiService {
    @GET("soal_butawarna.json")
    suspend fun getSoal(): List<Butawarna>
}