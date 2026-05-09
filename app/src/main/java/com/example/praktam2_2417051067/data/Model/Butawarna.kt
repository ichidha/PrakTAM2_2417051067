package com.example.praktam2_2417051067.data.Model

import com.google.gson.annotations.SerializedName

data class Butawarna(
    @SerializedName("name")
    val name: String,

    @SerializedName("pertanyaan")
    val pertanyaan: String,

    @SerializedName("jawaban")
    val jawaban: Int,

    @SerializedName("image_url")
    val imageUrl: String
)