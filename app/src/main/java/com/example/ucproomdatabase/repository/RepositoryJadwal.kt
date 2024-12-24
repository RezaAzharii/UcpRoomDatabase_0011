package com.example.ucproomdatabase.repository

import com.example.ucproomdatabase.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJadwal {
    suspend fun insertJad (jadwal: Jadwal)
    suspend fun deleteJad (jadwal: Jadwal)
    suspend fun updateJad (jadwal: Jadwal)
    fun getAllJad(): Flow<List<Jadwal>>
    fun getJad(idJ: Int): Flow<Jadwal>
}

