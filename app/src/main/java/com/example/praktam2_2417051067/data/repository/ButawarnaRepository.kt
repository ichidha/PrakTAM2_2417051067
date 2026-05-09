package com.example.praktam2_2417051067.data.repository

import com.example.praktam2_2417051067.data.api.RetrofitClient
import com.example.praktam2_2417051067.data.Model.Butawarna

class ButawarnaRepository {
    suspend fun getSoal (): List<Butawarna> {
        return try {
            RetrofitClient.instance.getSoal()
        } catch (e: Exception) {
            emptyList()
        }
    }
}