package com.example.ucproomdatabase.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dokter")
data class Dokter (
    @PrimaryKey(autoGenerate = true)
    val idD: Int = 0,
    val nama: String,
    val spesialis: String,
    val klinik: String,
    val nohp: String,
    val jamKerja: String
)