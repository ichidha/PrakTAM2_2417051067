package com.example.praktam2_2417051067.network

import Model.Butawarna
import retrofit2.http.GET

interface ApiService {
    @GET("soal_butawarna.json")
    suspend fun getSoal(): List<Butawarna>
}