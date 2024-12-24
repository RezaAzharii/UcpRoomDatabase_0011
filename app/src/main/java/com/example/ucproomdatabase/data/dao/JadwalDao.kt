package com.example.ucproomdatabase.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucproomdatabase.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {
    @Insert
    suspend fun insertJadwal(jadwal: Jadwal)
    @Query("SELECT * FROM jadwal ORDER BY idJ ASC")
    fun getAllJadwal(): Flow<List<Jadwal>>
    @Query("SELECT * FROM jadwal WHERE idJ = :idJ")
    fun getJadwal(idJ: Int) : Flow<Jadwal>
    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal)
    @Update
    suspend fun updateJadwal(jadwal: Jadwal)
}