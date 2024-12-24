package com.example.ucproomdatabase.repository

import com.example.ucproomdatabase.data.entity.Dokter
import com.example.ucproomdatabase.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryDokter {
    suspend fun insertDok (dokter: Dokter)
    fun getAllDok(): Flow<List<Dokter>>
}