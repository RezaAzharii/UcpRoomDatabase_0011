package com.example.ucproomdatabase.repository

import com.example.ucproomdatabase.data.dao.DokterDao
import com.example.ucproomdatabase.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDokter(
    private val dokterDao: DokterDao
) : RepositoryDokter{
    override suspend fun insertDok(dokter: Dokter){
        dokterDao.insertDokter(dokter)
    }
    override fun getAllDok(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }

}