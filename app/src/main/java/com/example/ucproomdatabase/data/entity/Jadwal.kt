package com.example.ucproomdatabase.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Jadwal")
data class Jadwal(
    @PrimaryKey (autoGenerate = true)
    val idJ: Int = 0,
    val namaDokter: String,
    val namaPasien: String,
    val noHp: String,
    val tglKonsul: String,
    val status: String
)