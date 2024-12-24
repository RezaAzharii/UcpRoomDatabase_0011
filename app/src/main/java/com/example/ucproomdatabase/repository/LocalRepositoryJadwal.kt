package com.example.ucproomdatabase.repository

import com.example.ucproomdatabase.data.dao.JadwalDao
import com.example.ucproomdatabase.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

class LocalRepositoryJadwal(
    private val jadwalDao: JadwalDao
) : RepositoryJadwal {
    override suspend fun insertJad(jadwal: Jadwal) {
        jadwalDao.insertJadwal(jadwal)
    }
    override suspend fun deleteJad(jadwal: Jadwal) {
        jadwalDao.deleteJadwal(jadwal)
    }
    override suspend fun updateJad(jadwal: Jadwal) {
        jadwalDao.updateJadwal(jadwal)
    }
    override fun getAllJad(): Flow<List<Jadwal>> {
        return jadwalDao.getAllJadwal()
    }
    override fun getJad(idJ: Int): Flow<Jadwal> {
        return jadwalDao.getJadwal(idJ)
    }

}
