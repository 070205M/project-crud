package com.example.hewannusantara

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hewan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val latin: String,
    val deskripsi: String,
    val imageUrl: String
)
